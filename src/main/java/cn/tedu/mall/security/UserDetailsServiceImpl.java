package cn.tedu.mall.security;

import cn.tedu.mall.exception.ServiceException;
import cn.tedu.mall.mapper.UserMapper;
import cn.tedu.mall.pojo.User;
import cn.tedu.mall.pojo.domain.UserAuthority;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName UserDetailsServiceImpl
 * @Version 1.0
 * @Description TODO
 * @Date 2022/12/22、下午2:32
 */
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("loadUserByUsername方法調用");
        User loginResult = userMapper.getByUsername(username);

        log.debug("獲取到的用戶訊息>>>{}",loginResult.toString());


        //測試用 要刪除的
        UserAuthority userAuthority = new UserAuthority();
        userAuthority.setAuthority("ROLE_user");
        List<UserAuthority> authorities = loginResult.getAuthorities();
        authorities.add(userAuthority);
        loginResult.setAuthorities(authorities);

        log.debug("封裝後的用戶訊息>>>{}",loginResult.toString());

        if(loginResult != null){
         CustomerDetails customerDetails = new CustomerDetails(
                 loginResult.getId(),
                 loginResult.getUsername(),
                 loginResult.getPassword(),
                 loginResult.getIsEnable() ==1,
                 loginResult.getAuthorities()
            );
            return customerDetails;
        }
       return null;
    }
}
