package cn.tedu.mall.service.impl;

import cn.tedu.mall.exception.ServiceException;
import cn.tedu.mall.mapper.UserMapper;
import cn.tedu.mall.pojo.User;
import cn.tedu.mall.pojo.UserLoginDTO;
import cn.tedu.mall.pojo.UserRegDTO;
import cn.tedu.mall.pojo.domain.UserAuthority;
import cn.tedu.mall.security.CustomerDetails;
import cn.tedu.mall.service.IUserService;
import cn.tedu.mall.utils.ConstUtils;
import cn.tedu.mall.utils.JwtUtils;
import cn.tedu.mall.web.ServiceCode;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        //預設帳號啟用
        user.setIsEnable(ConstUtils.IS_ENABLE);
        //初始化積分
        user.setRewardPoint(0);
        //設定權限 (待完成
//        List<UserAuthority> authority = new ArrayList<>();
//        authority.add(ConstUtils.getAuthority(ConstUtils.AUTHORITY_USER));
//        user.setAuthorities(authority);
        //資料寫入資料庫
        userMapper.insert(user);
        log.debug("註冊完成");
    }

    @Override
    public String login(UserLoginDTO userLoginDTO) {
        log.debug("開始登入service.login");

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userLoginDTO.getUsername(), userLoginDTO.getPassword()
        );
        Authentication loginResult = authenticationManager.authenticate(authentication);

        CustomerDetails principal =(CustomerDetails) loginResult.getPrincipal();

        Long id = principal.getId();
        String username = principal.getUsername();
        Collection<GrantedAuthority> authorities = principal.getAuthorities();

        String jsonString = JSON.toJSONString(authorities);

        Map<String,Object> claims = new HashMap<>();

        claims.put("id",id);
        claims.put("username",username);
        claims.put("authorities",jsonString);

        return JwtUtils.generate(claims);
    }
}
