package com.george.seckill.resolver;

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
        //获取cookie值
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = nativeWebRequest.getNativeRequest(HttpServletResponse.class);
        String cookieId = CookieUtil.readLoginToken(request, UserUtil.COOKIE_NAME_TOKEN);
        if(StringUtils.isEmpty(cookieId)){
            return null;
        }
        //从缓存中查找
        UserPO userPO = redisService.get(cookieId, UserPO.class);
        //在有效期内从redis获取到key之后，需要将key重新设置一下，从而达到延长有效期的效果
        if (userPO != null) {
            redisService.set(cookieId,userPO, CacheUtil.COOKIE_CACHE_TIME);
            CookieUtil.writeLoginToken(response,cookieId,UserUtil.COOKIE_NAME_TOKEN);
        }
        return userPO;
    }
}
