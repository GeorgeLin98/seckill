package com.george.seckill.annotation;

import com.george.seckill.api.user.pojo.UserPO;

/**
 * @description 保存用户线程Map
 * @date 2022.02.17
 * @author linzhuangze
 */
public class UserContext {
    // 保存用户的容器
    private static ThreadLocal<UserPO> userHolder = new ThreadLocal<>();

    public static void setUser(UserPO user) {
        userHolder.set(user);
    }

    public static UserPO getUser() {
        return userHolder.get();
    }
}
