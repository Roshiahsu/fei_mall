package cn.tedu.mall.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName MybatisConfiguration
 * @Version 1.0
 * @Description Mybatis 配置
 * @Date 2022/12/21、下午7:59
 */
@Configuration
@MapperScan("cn.tedu.mall.mapper")
public class MybatisConfiguration {
}
