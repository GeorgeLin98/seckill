package com.george.seckill.resolver;

import com.george.seckill.annotation.UserContext;
import com.george.seckill.api.cache.service.IRedisService;
import com.george.seckill.api.cache.util.CacheUtil;
import com.george.seckill.api.user.pojo.UserPO;
import com.george.seckill.api.user.util.UserUtil;
import com.george.seckill.util.CookieUtil;
import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description 解析请求，并将请求的参数设置到方法参数中
 * @date 2021.01.13
 * @author linzhuangze
 */
@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    //rpc调用
    @Reference(interfaceClass = IRedisService.class)
    IRedisService redisService;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> parameterType = methodParameter.getParameterType();
        return parameterType == UserPO.class;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        //从线程Map中获取
        UserPO user = UserContext.getUser();
        return user;
    }
}
