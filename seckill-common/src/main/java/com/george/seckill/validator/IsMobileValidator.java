package com.george.seckill.validator;

import com.george.seckill.util.ValidatorUtil;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @description isMobile注解处理类
 * @date 2021.01.12
 * @author linzhuangze
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile,String> {
    /**
     * 用于获取检验字段是否可以为空
     */
    private boolean required;

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        //如果必填直接校验
        if (required) {
            return ValidatorUtil.isMobile(value);
        //否则还需校验是否为空再校验格式
        } else {
            return StringUtils.isEmpty(value) || ValidatorUtil.isMobile(value);
        }
    }
}
