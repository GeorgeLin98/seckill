package com.george.seckill.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * @description 校验工具类
 * @date 2021.01.11
 * @author  linzhuangze
 */
public class ValidatorUtil {

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";;

    /**
     * @description 校验手机号
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        if (StringUtils.isEmpty(mobile))
            return false;

        return Pattern.matches(REGEX_MOBILE, mobile);
    }

}
