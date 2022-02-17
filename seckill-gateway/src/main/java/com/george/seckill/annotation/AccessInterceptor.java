package com.george.seckill.annotation;

import com.alibaba.fastjson.JSON;
import com.george.seckill.api.cache.service.IRedisService;
import com.george.seckill.api.cache.util.CacheUtil;
import com.george.seckill.api.good.util.GoodUtil;
import com.george.seckill.api.user.pojo.UserPO;
import com.george.seckill.api.user.service.IUserService;
import com.george.seckill.api.user.util.UserUtil;
import com.george.seckill.pojo.ResponseVO;
import com.george.seckill.util.CookieUtil;
import com.george.seckill.util.MsgAndCodeEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * @description 拦截器
 * @date 2022.02.17
 * @author linzhuangze
 */
@Service
public class AccessInterceptor extends HandlerInterceptorAdapter {

    @Reference(interfaceClass = IUserService.class)
    IUserService userService;

    @Reference(interfaceClass = IRedisService.class)
    IRedisService redisService;

    public static final String CONTENT_TYPE_JSON = "application/json;charset=UTF-8";

    /**
     * @description 执行目标方法前执行
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //拦截SpringMVC方法
        if (handler instanceof HandlerMethod) {
            // 获取用户对象
            UserPO user = this.getUser(request, response);
            //保存用户到ThreadLocal,这样同一个线程访问的是同一个用户
            UserContext.setUser(user);
            // 获取标注了 @AccessLimit 的方法,没有注解则直接返回
            HandlerMethod hm = (HandlerMethod) handler;
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            if (accessLimit == null)
                return true;
            // 获取注解的元素值
            int seconds = accessLimit.seconds();
            int maxCount = accessLimit.maxAccessCount();
            boolean needLogin = accessLimit.needLogin();
            String uri = request.getRequestURI();
            if (needLogin) {
                if(null == user){
                    //如果必须登录而未登陆则返回报错
                    render(response, MsgAndCodeEnum.FAIL.getCode(),MsgAndCodeEnum.FAIL.getMsg());
                    return false;
                }
            }
            //在redis中存储的访问次数
            Integer count = redisService.get(String.format(GoodUtil.CURRENT_LIMITING_KEY,uri,user.getPhone()),Integer.class);
            //第一次重复点击 秒杀按钮
            if (count == null) {
                redisService.set(String.format(GoodUtil.CURRENT_LIMITING_KEY,uri,user.getPhone()), 1, seconds);
                //点击次数未达最大值
            } else if (count < maxCount) {
                redisService.incr(String.format(GoodUtil.CURRENT_LIMITING_KEY,uri,user.getPhone()));
                //点击次数已满
            } else {
                this.render(response, MsgAndCodeEnum.ACCESS_LIMIT_REACHED.getCode(),MsgAndCodeEnum.ACCESS_LIMIT_REACHED.getMsg());
                return false;
            }
        }
        return true;
    }

    /**
     * @description 渲染返回数据
     * @param response
     * @param code
     * @param msg
     * @throws Exception
     */
    private void render(HttpServletResponse response, int code,String msg) throws Exception {
        response.setContentType(CONTENT_TYPE_JSON);
        OutputStream out = response.getOutputStream();
        String str = JSON.toJSONString(ResponseVO.fail(code,msg));
        out.write(str.getBytes(StandardCharsets.UTF_8));
        out.flush();
        out.close();
    }

    /**
     * @description 获取用户
     * @param request
     * @param response
     * @return
     */
    private UserPO getUser(HttpServletRequest request, HttpServletResponse response) {
        String cookieId = CookieUtil.readLoginToken(request, UserUtil.COOKIE_NAME_TOKEN);
        if(StringUtils.isEmpty(cookieId)){
            return null;
        }
        //从缓存中查找
        UserPO userPO = redisService.get(cookieId, UserPO.class);
        return userPO;
    }
}
