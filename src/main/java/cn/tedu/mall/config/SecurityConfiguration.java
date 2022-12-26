package cn.tedu.mall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @ClassName SecurityConfiguration
 * @Version 1.0
 * @Description TODO
 * @Date 2022/12/22、上午12:56
 */
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.cors();

        String[] urls = {
                "/admins/login",
                "/doc.html",
                "/**/*.css",
                "/**/*.js",
                "/swagger-resources",
                "/v2/api-docs",
        };

//        http.authorizeRequests() //請求授權
//                .antMatchers(urls)
//                .permitAll()     //允許直接訪問
//                .anyRequest()    //除上述配置以外的其他請求
//                .authenticated();//通過認證：已經登入過才能訪問
        http.authorizeRequests() //請求授權
                .anyRequest()
                .permitAll()     ;//允許直接訪問
                    //除上述配置以外的其他請求

    }

}
