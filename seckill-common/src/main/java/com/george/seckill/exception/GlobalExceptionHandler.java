package com.george.seckill.exception;

import com.george.seckill.pojo.ResponseVO;
import com.george.seckill.util.MsgAndCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    /**
     * @description 处理异常
     * 1、@ExceptionHandler：这个注解用指定这个方法对何种异常处理（这里默认所有异常都用这个方法处理）
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseVO<String> exceptionHandler(Exception e) {
        logger.info("--------------出现异常--------------");
        // 打印原始的异常信息，方便调试
        e.printStackTrace();
        // 如果所拦截的异常是自定义的全局异常，这按自定义异常的处理方式处理，否则按默认方式处理
        if (e instanceof GlobalException) {
            logger.debug("common 模块中的异常");
            GlobalException exception = (GlobalException) e;
            // 向客户端返回异常信息
            return ResponseVO.fail(exception.getCode(),exception.getMsg());
        } else if (e instanceof BindException) {
            BindException bindException = (BindException) e;
            List<ObjectError> errors = bindException.getAllErrors();
            // 这里只获取了第一个错误对象
            ObjectError error = errors.get(0);
            // 获取其中的信息
            String message = error.getDefaultMessage();
            // 将错误信息动态地拼接到已定义的部分信息上
            return ResponseVO.fail(MsgAndCodeEnum.BIND_ERROR.getCode(),MsgAndCodeEnum.BIND_ERROR.fillArgs(message));
        } else {
            return ResponseVO.fail(MsgAndCodeEnum.FAIL.getCode(), MsgAndCodeEnum.FAIL.getMsg());
        }
    }
}
