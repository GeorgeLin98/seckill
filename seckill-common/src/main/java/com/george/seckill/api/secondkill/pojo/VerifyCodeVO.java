package com.george.seckill.api.secondkill.pojo;

import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * @description 秒杀验证VO
 * @date 2021.01.13
 * @author linzhuanzge
 */
public class VerifyCodeVO implements Serializable {
    /**
     * 验证码图片
     */
    private BufferedImage image;

    /**
     * 验证码计算结果
     */
    private int expResult;

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public int getExpResult() {
        return expResult;
    }

    public void setExpResult(int expResult) {
        this.expResult = expResult;
    }
}
