package cn.tedu.mall.service.impl;

import cn.tedu.mall.exception.ServiceException;
import cn.tedu.mall.mapper.UserMapper;
import cn.tedu.mall.pojo.password.PasswordDTO;
import cn.tedu.mall.pojo.user.UserInfoVO;
import cn.tedu.mall.pojo.user.UserLoginVO;
import cn.tedu.mall.pojo.user.UserUpdateDTO;
import cn.tedu.mall.service.IMailService;
import cn.tedu.mall.service.IPasswordService;
import cn.tedu.mall.utils.ConstUtils;
import cn.tedu.mall.web.ServiceCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.xml.ws.ServiceMode;

/**
 * @ClassName PasswordServiceImpl
 * @Version 1.0
 * @Description TODO
 * @Date 2023/2/10、上午10:26
 */
@Service
@Slf4j
public class PasswordServiceImpl implements IPasswordService {

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private final IMailService mailService;

    @Autowired
    public PasswordServiceImpl(PasswordEncoder passwordEncoder,
                               UserMapper userMapper,
                               IMailService mailService) {
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.mailService = mailService;
    }

    /**
     * 欲生成的密碼長度
     */
    public static final int INIT_PASSWORD_LENGTH = 6;

    /**
     * 驗證原始密碼
     * @param passwordDTO
     * @return
     */
    @Override
    public void matchesPassword(PasswordDTO passwordDTO) {
        log.debug("開始驗證原始密碼");
        //獲取用戶id
        Long userId = ConstUtils.getUserId();
        //根據UserId獲取用戶資料
        UserInfoVO userInfoVO = userMapper.userInfo(userId);
        //獲取用戶密碼
        String passwordBySql =  userInfoVO.getPassword();
        if (!passwordEncoder.matches(passwordDTO.getOldPassword(),passwordBySql)){
            throw new ServiceException(ServiceCode.ERR_BAD_REQUEST,"密碼錯誤!!");
        }
        updatePassword(passwordDTO,userId);
    }

    /**
     * 修改用戶密碼
     * @param passwordDTO
     */
    public void updatePassword(PasswordDTO passwordDTO,Long userId) {
        log.debug("開始service.updatePassword");
        /*密碼加密*/
        //獲取新密碼
        String password = passwordDTO.getNewPassword();
        //新密碼加密
        String encode = passwordEncoder.encode(password);
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setPassword(encode);
        userUpdateDTO.setId(userId);

        int rows = userMapper.update(userUpdateDTO);
        if(rows !=1){
            throw new ServiceException(ServiceCode.ERR_UPDATE,"伺服器繁忙請稍後再試");
        }
    }

    /**
     * 重置密碼
     * @param username 用戶名稱
     */
    @Override
    public void resetPassword(String username) {
        log.debug("開始重置密碼");
        //查看用戶是否存在
        int count = userMapper.countByUsername(username);
        if(count <1){
            throw new ServiceException(ServiceCode.ERR_BAD_REQUEST,"查無此用戶！！");
        }
        //獲取用戶email
        UserLoginVO userInfo = userMapper.getByUsername(username);
        String email = userInfo.getEmail();
        //獲取隨機生成的密碼
        String newPassword = initPassword(INIT_PASSWORD_LENGTH);
        //密碼加密
        String encode = passwordEncoder.encode(newPassword);
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setPassword(encode);
        userUpdateDTO.setId(userInfo.getId());
        int rows = userMapper.update(userUpdateDTO);
        if(rows !=1){
            throw new ServiceException(ServiceCode.ERR_UPDATE,"伺服器繁忙請稍後再試");
        }

        String subject="重置的密碼";
        String body = "你重置的密碼【"+newPassword+"】!!";
        log.debug("email>>{}",email);

        mailService.sendEmail(email,subject,body);
    }

    /**
     * 生成隨機密碼
     * @param length 欲生成的密碼數
     * @return 隨機密碼
     */
    private String initPassword(int length){
        log.debug("開始生成隨機密碼");
        StringBuilder passwordString = new StringBuilder();
        for (int i = 0; i < length ; i++) {
            int random =(int)(Math.random()*(122-97+1))+97;
            passwordString.append((char)random);
        }
        log.debug("密碼：{}",passwordString.toString());
        return passwordString.toString();
    }
}
