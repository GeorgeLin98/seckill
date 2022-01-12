package com.george.seckill.user;

import com.george.seckill.api.user.pojo.LoginVO;
import com.george.seckill.api.user.pojo.RegisterVO;
import com.george.seckill.api.user.service.IUserService;
import com.george.seckill.api.user.util.UserUtil;
import com.george.seckill.exception.GlobalException;
import com.george.seckill.pojo.ResponseVO;
import com.george.seckill.util.MsgAndCodeEnum;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @description 用户controller
 * @date 2021.01.12
 * @author linzhuangze
 */
@Controller
@RequestMapping("user")
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Reference(interfaceClass = IUserService.class)
    IUserService userService;

    /**
     * @description 首页
     * @return
     */
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index() {
        logger.info("首页接口");
        // login页面
        return "login";
    }

    /**
     * @description 注册跳转
     * @return
     */
    @RequestMapping(value = "doRegister", method = RequestMethod.GET)
    public String doRegister() {
        logger.info("doRegister()");
        return "register";
    }

    /**
     * @description 注册接口
     * @param
     * @return
     */
    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVO register(@Valid RegisterVO registerVO) {
        if (registerVO == null) {
            throw new GlobalException(MsgAndCodeEnum.FILL_REGISTER_INFO.getCode(),MsgAndCodeEnum.FILL_REGISTER_INFO.getMsg());
        }
        userService.register(registerVO);
        return ResponseVO.success();
    }

    /**
     * @description 用户登录
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVO login(@Valid LoginVO loginVO) {
        userService.login(loginVO);
        // 返回登陆成功
        return ResponseVO.success();
    }
}
