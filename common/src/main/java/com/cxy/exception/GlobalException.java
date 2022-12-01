package com.cxy.exception;

import com.cxy.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class GlobalException {
    //自定义通用异常处理
    @ExceptionHandler(MyException.class)
    public Result MyException(MyException e) {
        return Result.fail().code(e.getCode()).message(e.getMessage());
    }

    //全局异常
    @ExceptionHandler(Exception.class)
    public Result gException(Exception e) {
        e.printStackTrace();
        return Result.fail().message("触发全局异常");
    }

}
