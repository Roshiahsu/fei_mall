package cn.tedu.mall.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName WebMvcConfiguration
 * @Version 1.0
 * @Description TODO 跨域配置
 * @Date 2022/12/27、上午5:26
 */

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")//表示所有路徑
                .allowedOrigins("http://localhost:9080/")
                .allowedOrigins("http://localhost:8080/")
//                .allowedOriginPatterns("*")//允許任何方法源
                .allowedMethods("*")//允許任何請求方式 get or post
                .allowedHeaders("*")//允許任何請求頭
                .allowCredentials(true)//允許發送cookie
                .maxAge(3600);
    }
}
