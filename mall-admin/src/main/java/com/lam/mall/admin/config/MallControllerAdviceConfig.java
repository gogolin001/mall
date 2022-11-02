package com.lam.mall.admin.config;


import com.lam.mall.common.api.CommonResult;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ControllerAdvice
public class MallControllerAdviceConfig {

    @ExceptionHandler(BindException.class)
    public CommonResult validExceptionHandler(BindException exception) {
        return CommonResult.validateFailed(exception.getAllErrors().get(0).getDefaultMessage());
    }
}
