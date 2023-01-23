package cn.tedu.mall.service.impl;

import cn.tedu.mall.exception.ServiceException;
import cn.tedu.mall.mapper.UserAddressMapper;
import cn.tedu.mall.mapper.UserMapper;
import cn.tedu.mall.pojo.user.*;
import cn.tedu.mall.security.CustomerDetails;
import cn.tedu.mall.security.LoginPrinciple;
import cn.tedu.mall.service.IUserService;
import cn.tedu.mall.utils.ConstUtils;
import cn.tedu.mall.utils.JwtUtils;
import cn.tedu.mall.web.ServiceCode;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @ClassName UserServiceImpl
 * @Version 1.0
 * @Description 顧客中心service
 * @Date 2022/12/21、下午9:34
 */
@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 用戶註冊
     * @param userRegDTO
     */
    @Override
    public void reg(UserRegDTO userRegDTO) {
        log.debug("開始service.reg");
        /*使用username查詢是否重複註冊*/
        int count = userMapper.countByUsername(userRegDTO.getUsername());
        if(count > 0){
            throw new ServiceException(ServiceCode.ERR_BAD_REQUEST,"用戶名稱已被註冊!");
        }

        /*準備要寫入資料庫的資料*/
        User user = new User();
        user.setUsername(userRegDTO.getUsername());
        //密碼加密
        String encode = passwordEncoder.encode(userRegDTO.getPassword());
        user.setPassword(encode);

        /*補充用戶資料*/
        //預設用戶暱稱為註冊帳號
        user.setNickname(userRegDTO.getUsername());

        //資料寫入資料庫
        int rows = userMapper.insert(user);
        if(rows != 1){
            throw new ServiceException(ServiceCode.ERR_INSERT,"伺服器忙碌中，請稍候再試!");
        }
        log.debug("註冊完成");
    }

    /**
     * 用戶登入
     * @param userLoginDTO
     * @return
     */
    @Override
    public String login(UserLoginDTO userLoginDTO) {
        log.debug("開始登入service.login");
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userLoginDTO.getUsername(), userLoginDTO.getPassword()
        );
        Authentication loginResult = authenticationManager.authenticate(authentication);

        CustomerDetails principal =(CustomerDetails) loginResult.getPrincipal();

        //獲取用戶id
        Long id = principal.getId();
        //獲取用戶名稱
        String username = principal.getUsername();
        //獲取用戶權限
        Collection<GrantedAuthority> authorities = principal.getAuthorities();
        //將權限轉換成List<String>類型
        List<String> authoritiesValue = new ArrayList<>();
        for (GrantedAuthority authority : authorities) {
            String value = authority.getAuthority();
            authoritiesValue.add(value);
        }
        //使用自定義類LoginPrinciple封裝資料
        LoginPrinciple loginPrinciple = new LoginPrinciple(id, username, authoritiesValue);
        //fastjson將 LoginPrinciple 轉換成json格式
        String loginPrincipleString = JSON.toJSONString(loginPrinciple);

        Map<String,Object> claims = new HashMap<>();
        claims.put(ConstUtils.CLAIM_KEY_USERNAME,loginPrincipleString);

        return JwtUtils.generate(claims);
    }

    /**
     * 獲取用戶詳情
     * @return
     */
    @Override
    public UserInfoVO userInfo() {
        log.debug("開始service.userInfo");
        //從上下文獲取userId
        Long userId = ConstUtils.getUserId();
        UserInfoVO userInfoVO = userMapper.userInfo(userId);
        //拼接地址詳情
        StringBuffer buffer = new StringBuffer();
        String detailAddress = userInfoVO.getDetailedAddress();
        if (userInfoVO.getZipCode() !=null && !detailAddress.contains(userInfoVO.getZipCode())){
            buffer.append(userInfoVO.getZipCode());
        }
        if (userInfoVO.getCity() !=null && !detailAddress.contains(userInfoVO.getCity())){
            buffer.append(userInfoVO.getCity());
        }
        if (userInfoVO.getZone() !=null && !detailAddress.contains(userInfoVO.getZone())){
            buffer.append(userInfoVO.getZone());
        }
        buffer.append(userInfoVO.getDetailedAddress());


        userInfoVO.setDetailedAddress(buffer.toString());
        log.debug("準備響應的用戶資料>>>{}",userInfoVO);
        if(userInfoVO ==null){
            throw new ServiceException(ServiceCode.ERR_UNKNOWN,"伺服器繁忙請稍後再試");
        }
        return userInfoVO;
    }

    /**
     * 修改用戶詳情，不包含修改密碼
     * @param userUpdateDTO
     */
    @Override
    public void update(UserUpdateDTO userUpdateDTO) {
        log.debug("開始service.update");
        LocalDateTime bod = userUpdateDTO.getBod();
        //LocalDateTime 沒有時區的分別，所以前端傳來的時間會是UTC+0，手動加8小時
        bod=bod.plusHours(8);

        log.debug("BOD>>>{}",bod);
        userUpdateDTO.setBod(bod);
        int rows = userMapper.update(userUpdateDTO);
        if(rows !=1){
            throw new ServiceException(ServiceCode.ERR_UPDATE,"伺服器繁忙請稍後再試");
        }
    }

    /**
     * 修改用戶密碼
     * @param userUpdateDTO
     */
    @Override
    public void updatePassword(UserUpdateDTO userUpdateDTO) {
        log.debug("開始service.updatePassword");

        /*密碼加密*/
        String password = userUpdateDTO.getPassword();
        String encode = passwordEncoder.encode(password);
        userUpdateDTO.setPassword(encode);

        int rows = userMapper.update(userUpdateDTO);
        if(rows !=1){
            throw new ServiceException(ServiceCode.ERR_UPDATE,"伺服器繁忙請稍後再試");
        }
    }
}
