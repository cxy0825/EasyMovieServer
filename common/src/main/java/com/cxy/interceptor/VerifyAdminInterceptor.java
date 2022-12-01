package com.cxy.interceptor;


import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import com.alibaba.fastjson.JSONObject;
import com.cxy.Utils.ThreadLocalUtil;
import com.cxy.result.Result;
import com.cxy.result.ResultEnum;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//针对后台管理员级别的token验证拦截器
public class VerifyAdminInterceptor implements HandlerInterceptor {
    //权限等级 至少是管理员级以上
    final int POWER_LEVEL = 1;
    //权限类型 必须是admin
    final String POWER_TYPE = "admin";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获得请求头中的token
        String token = request.getHeader("token");
        //判空验证
        if (StringUtils.isEmpty(token)) {
            this.loginOut(response);
            return false;
        }
        //鉴定权限
        JWT jwt = null;
        try {
            jwt = JWTUtil.parseToken(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (jwt == null) {
            loginOut(response);
        }

        String type = (String) jwt.getPayload("type");
        Integer power = (Integer) jwt.getPayload("power");
        //写入到threadlocal中
        JWTPayload payload = jwt.getPayload();
        ThreadLocalUtil.set(payload);
        //超级管理员就直接过
        if (type.equals("root")) {
            return true;
        }
        //如果是普通管理员就放过
        else if (POWER_TYPE.equals(type) && power >= POWER_LEVEL) {
            return true;
        }
        //不是普通管理员并且权限等级等于1的拦截
        loginOut(response);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtil.remove();
    }

    public void loginOut(HttpServletResponse response) throws IOException {
        Result fail = Result.fail(ResultEnum.LOGIN_EXPIRES);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(JSONObject.toJSONString(fail));
        writer.close();
    }
}
