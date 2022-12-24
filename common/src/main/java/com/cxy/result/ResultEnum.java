package com.cxy.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum ResultEnum {
    OK(20000, "成功"),
    FAIL(10000, "内部失败"),
    MISS_PARAMS(10001, "缺少参数"),
    ERROR_PARAMS(10002, "错误的参数"),
    MISS_USER(10003, "该用户不存在或密码错误"),
    LOGIN_EXPIRES(10004, "登录过期"),
    TIME_OVERLAP(10005, "放映厅播放影片时间重叠"),
    PROJECT_ERROR(10006, "商品不存在"),
    PAYMENT_ERROR(10007, "支付回调失败"),
    PAYMENT_SIGN_FAIL(10008, "支付回调验证签名失败"),
    CODE_ERROR(10009, "验证码错误");

    private int code;
    private String message;

}
