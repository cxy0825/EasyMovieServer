package com.cxy.common;

import lombok.Data;

@Data
public class r<T> {
    Integer code;
    String message;
    T data;

    public static r ok() {
        r result = new r();
        result.setCode(20000);
        result.setMessage("成功");
        return result;
    }

    public static r file() {
        r result = new r();
        result.setCode(10000);
        result.setMessage("失败了");
        return result;
    }

    public r code(Integer code) {
        this.code = code;
        return this;
    }

    public r message(String message) {
        this.message = message;
        return this;
    }

    public r data(T data) {
        this.data = data;
        return this;
    }
}
