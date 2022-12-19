package com.cxy.interceptor;


import cn.hutool.json.JSONUtil;
import com.cxy.Utils.ThreadLocalUtil;
import com.cxy.entry.Token;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

//针对后台管理员级别的token验证拦截器
public class VerifyAdminInterceptor implements HandlerInterceptor {
    //权限等级 至少是管理员级以上
    final int POWER_LEVEL = 1;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userInfo = request.getHeader("userInfo");

        Token token = JSONUtil.toBean(userInfo, Token.class);
        //如果是普通用户
        if (!token.getType().equals("root") || !token.getType().equals("admin")) {
            ThreadLocalUtil.set(token);
            return true;
        }
        //如果是管理员用户
        //等级够不够
        try {
            if (Long.valueOf(token.getPower()) < 1) {
                response.setContentType("application/json");
                response.setCharacterEncoding("utf8");
                PrintWriter writer = response.getWriter();
                writer.write("{\"code\": 10004,\"message\": \"权限不足\"}");
                return false;
            }
            ThreadLocalUtil.set(token);
            return true;
        } catch (Exception e) {
            response.setContentType("application/json");
            response.setCharacterEncoding("utf8");
            PrintWriter writer = response.getWriter();
            writer.write("{\"code\": 10004,\"message\": \"权限不足\"}");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtil.remove();
    }


}
