package com.cxy.result;

import lombok.Data;

@Data
public class Result<T> {
    int code;
    String message;
    T data;

    private Result() {
    }

    public static Result ok() {
        Result result = new Result();
        result.setCode(ResultEnum.OK.getCode());
        result.setMessage(ResultEnum.OK.getMessage());
        return result;
    }

    public static Result fail() {
        Result result = new Result();
        result.setCode(ResultEnum.FAIL.getCode());
        result.setMessage(ResultEnum.FAIL.getMessage());
        return result;
    }

    public static Result fail(ResultEnum resultEnum) {
        Result result = new Result();
        result.setCode(resultEnum.getCode());
        result.setMessage(resultEnum.getMessage());
        return result;
    }

    public Result data(T data) {
        this.setData(data);
        return this;
    }

    //    设置特定的结果
    public Result message(String message) {
        this.setMessage(message);
        return this;
    }

    //    设置特定的结果
    public Result code(Integer code) {
        this.setCode(code);
        return this;
    }
}
