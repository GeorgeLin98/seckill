package com.george.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.george.seckill.api.cache.service.IRedisService;
import com.george.seckill.api.cache.util.CacheUtil;
import com.george.seckill.api.user.pojo.LoginVO;
import com.george.seckill.api.user.pojo.RegisterVO;
import com.george.seckill.api.user.pojo.UserPO;
import com.george.seckill.api.user.service.IUserService;
import com.george.seckill.api.user.util.UserUtil;
import com.george.seckill.exception.GlobalException;
import com.george.seckill.mapper.UserMapper;
import com.george.seckill.util.CookieUtil;
import com.george.seckill.util.MD5Util;
import com.george.seckill.util.MsgAndCodeEnum;
import com.george.seckill.util.UUIDUtil;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @description 用户service实现类
 * @date 2021.01.12
 * @author linzhuangze
 */
@Transactional
@Service(interfaceClass = IUserService.class)
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMapper userMapper;
    //rpc调用
    @Reference(interfaceClass = IRedisService.class)
    private IRedisService redisService;

    @Override
    public void register(RegisterVO userModel) {
        // 检查用户是否注册
        UserPO user = this.getInfoByUsername(userModel.getPhone());
        // 用户已经注册
        if (user == null) {
            throw new GlobalException(MsgAndCodeEnum.USER_EXIST.getCode(),MsgAndCodeEnum.USER_EXIST.getMsg());
        }
        // 生成UserPO对象
        UserPO newUser = new UserPO();
        newUser.setPhone(userModel.getPhone());
        newUser.setNickname(userModel.getNickname());
        newUser.setHead(userModel.getHead());
        newUser.setSalt(MD5Util.SALT);
        String dbPass = MD5Util.formPassToDbPass(userModel.getPassword(), MD5Util.SALT);
        newUser.setPassword(dbPass);
        newUser.setRegisterDate(new Date(System.currentTimeMillis()));
        // 写入数据库
        userMapper.insert(newUser);
    }

    @Override
    public UserPO getInfoByUsername(Long username) {
        QueryWrapper<UserPO> wrapper = new QueryWrapper<>();
        wrapper.eq("phone",username);
        UserPO userPO = userMapper.selectOne(wrapper);
        return userPO;
    }

    @Override
    public void login(LoginVO loginVO) {
        Long phone = loginVO.getPhone();
        String password = loginVO.getPassword();
        //根据帐号从数据库获取用户信息
        UserPO userPO = userMapper.selectById(phone);
        // 缓存中、数据库中都不存在该用户信息，直接返回
        if (userPO == null)
            throw new GlobalException(MsgAndCodeEnum.MOBILE_NOT_EXIST.getCode(),MsgAndCodeEnum.MOBILE_NOT_EXIST.getMsg());
        // 判断手机号对应的密码是否一致
        String dbPassword = userPO.getPassword();
        String dbSalt = userPO.getSalt();
        String calcPass = MD5Util.formPassToDbPass(password, dbSalt);
        if (!calcPass.equals(dbPassword))
            throw new GlobalException(MsgAndCodeEnum.PASSWORD_ERROR.getCode(),MsgAndCodeEnum.PASSWORD_ERROR.getMsg());
        // 生成cookie
        String cookieId = UUIDUtil.uuid();
        //对象信息存入redis
        redisService.set(cookieId,userPO, CacheUtil.CACHE_TIME_FOREVER);
        //存入response
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        CookieUtil.writeLoginToken(requestAttributes.getResponse(),cookieId, UserUtil.COOKIE_NAME_TOKEN);
    }

    @Override
    public UserPO getUserByPhone(long phone) {
        return null;
    }
}
