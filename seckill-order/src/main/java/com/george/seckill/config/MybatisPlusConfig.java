package com.george.seckill.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @description 配置mybatis-plus 和 数据源
 * @date 2021.01.13
 * @author linzhuangze
 */
@Configuration
@MapperScan(basePackages = "com.george.seckill.mapper")
public class MybatisPlusConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    public DataSource returnDruidDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        return druidDataSource;
    }

}
