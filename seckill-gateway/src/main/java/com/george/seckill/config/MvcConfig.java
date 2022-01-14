package com.george.seckill.config;

import com.george.seckill.resolver.UserArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @description webMVC配置
 * @date 2021.01.13
 * @author linzhuangze
 */
@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    private UserArgumentResolver userArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        // 添加自定义的参数解析器
        resolvers.add(userArgumentResolver);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //接替默认mvc配置后需要自己处理静态资源映射
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }
}
