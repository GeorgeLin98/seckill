package com.george.seckill.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description 手机号字段校验注解
 * @date 2021.01、12
 * @author linzhuangze
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
// 这个注解的参数指定用于校验工作的是哪个类
@Constraint(
        validatedBy = {IsMobileValidator.class}
)
public @interface IsMobile {
    /**
     * @description 默认手机号码不可为空
     * @return
     */
    boolean required() default true;

    String message() default "手机号码格式有误！";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
