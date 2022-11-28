package com.cxy.exception;

import com.cxy.result.ResultEnum;
import lombok.Data;

@Data
public class MyException extends RuntimeException {
    /**
     * 错误码
     */
    private int code;
    /**
     * 错误信息
     */
    private String message;

    public MyException(String message) {
        this.message = message;
    }

    public MyException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    //枚举类中获取错误对象
    public MyException(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
    }
}
