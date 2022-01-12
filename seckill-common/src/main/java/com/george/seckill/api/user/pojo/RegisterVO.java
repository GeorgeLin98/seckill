package com.george.seckill.api.user.pojo;

import com.george.seckill.validator.IsMobile;

import javax.validation.constraints.NotNull;

/**
 * @description 注册VO
 * @date 2021.01.12
 * @author linzhuangze
 */
public class RegisterVO {
    /**
     * 手机号
     */
    @IsMobile
    @NotNull
    private Long phone;
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

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
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
