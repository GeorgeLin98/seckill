package com.george.seckill.api.user.pojo;

import com.george.seckill.validator.IsMobile;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @description 登录VO
 * @date 2021.01.12
 * @author linzhuangze
 */
public class LoginVO {
    /**
     * 帐号：手机号
     */
    @NotNull
    // 自定义的注解完成手机号的校验
    @IsMobile
    private Long phone;
    /**
     * 密码
     */
    @NotNull
    //长度最小为32
    @Length(min = 32)
    private String password;

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
