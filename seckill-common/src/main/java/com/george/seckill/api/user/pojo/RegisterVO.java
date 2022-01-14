package com.george.seckill.api.user.pojo;

import com.george.seckill.validator.IsMobile;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @description 注册VO
 * @date 2021.01.12
 * @author linzhuangze
 */
public class RegisterVO implements Serializable {
    /**
     * 手机号
     */
    @IsMobile
    @NotNull
    private String phone;
    /**
     * 昵称
     */
    @NotNull
    private String nickname;
    /**
     * 头像
     */
    private String head;
    /**
     * 密码
     */
    @NotNull
    private String password;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
