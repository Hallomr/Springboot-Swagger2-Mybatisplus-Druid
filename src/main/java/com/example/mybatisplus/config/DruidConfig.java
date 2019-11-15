package com.example.mybatisplus.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/*数据源配置*/
@Configuration
public class DruidConfig {
    @Bean(name="dataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    public DataSource dataSource(){
        return new DruidDataSource();
    }
//    配置事物管理器
    /*多个事务管理器时 可以在@Transactional注解上声明使用哪个事务管理*/
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager(){
        return new DataSourceTransactionManager(dataSource());
    }

}
