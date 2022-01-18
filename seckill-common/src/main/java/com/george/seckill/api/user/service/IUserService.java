package com.george.seckill.api.user.service;

import com.george.seckill.api.user.pojo.LoginVO;
import com.george.seckill.api.user.pojo.RegisterVO;
import com.george.seckill.api.user.pojo.UserPO;

/**
 * @description 用户service接口
 * @date 2021.01.12
 * @author linzhuangze
 */
public interface IUserService {

    /**
     * @description 注册
     * @param userModel
     * @return
     */
    void register(RegisterVO userModel);

    /**
     * @description 检查用户名是否存在
     * @param username
     * @return
     */
    UserPO getInfoByUsername(Long username);

    /**
     * @description 登录
     * @param loginVO
     * @return
     */
    String login(LoginVO loginVO);

    /**
     * 根据phone获取用户
     * @param phone
     * @return
     */
    UserPO getUserByPhone(long phone);
}
