package com.george.seckill.api.user.pojo;

import com.george.seckill.validator.IsMobile;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @description 登录VO
 * @date 2021.01.12
 * @author linzhuangze
 */
public class LoginVO implements Serializable {
    /**
     * 帐号：手机号
     */
    @NotNull
    // 自定义的注解完成手机号的校验
    @IsMobile
    private String mobile;
    /**
     * 密码
     */
    @NotNull
    //长度最小为32
    @Length(min = 32)
    private String password;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
