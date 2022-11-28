package com.cxy.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.JWTValidator;
import com.cxy.entry.User;
import com.cxy.result.Result;
import com.cxy.result.ResultEnum;
import com.cxy.service.AuthorizationService;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthorizationImpl implements AuthorizationService {
    final byte[] key = "zxc5646@#@$".getBytes(StandardCharsets.UTF_8);
    DateTime now = DateUtil.date();
    DateTime tokenTime = DateUtil.offsetMinute(now, 5);
    DateTime refreshTokenTime = DateUtil.offsetDay(now, 10);


    @Override
    //生成 token 和 refreshToken
    public Map<String, Object> createToken(User user) {
        String token = JWT.create()
                .setPayload("ID", user.getId())
                .setPayload("phone", user.getPhone())
                .setPayload("power", user.getPower())
                .setIssuedAt(now)//签发时间
                .setExpiresAt(tokenTime)//过期时间
                .setKey(key)
                .sign();
        String refreshToken = JWT.create()
                .setPayload("ID", user.getId())
                .setPayload("phone", user.getPhone())
                .setPayload("power", user.getPower())
                .setIssuedAt(now)//签发时间
                .setExpiresAt(refreshTokenTime)//过期时间
                .setKey(key)
                .sign();
        HashMap<String, Object> tokenMap = new HashMap<>();

        tokenMap.put("token", token);
        tokenMap.put("refreshToken", refreshToken);
        return tokenMap;
    }

    //根据refreshToken生成token
    public String createTokenByRefreshToken(JWT refreshJwt) {
        String token = JWT.create()
                .setPayload("ID", refreshJwt.getPayload("ID"))
                .setPayload("phone", refreshJwt.getPayload("phone"))
                .setPayload("power", refreshJwt.getPayload("power"))
                .setIssuedAt(now)//签发时间
                .setExpiresAt(tokenTime)//过期时间
                .setKey(key)
                .sign();
        return token;
    }

    //刷新refreshToken
    public String refresh(JWT refreshJwt) {
        String refreshToken = JWT.create()
                .setPayload("ID", refreshJwt.getPayload("ID"))
                .setPayload("phone", refreshJwt.getPayload("phone"))
                .setPayload("power", refreshJwt.getPayload("power"))
                .setIssuedAt(now)//签发时间
                .setExpiresAt(refreshTokenTime)//过期时间
                .setKey(key)
                .sign();
        return refreshToken;
    }

    @Override
    public Boolean verify(String token) {
        //先验证token签名是否正确
        boolean verify;
        try {
            verify = JWTUtil.verify(token, key);
        } catch (Exception e) {
            return false;
        }
        if (!verify) {
            return false;
        }
        //验证token是否过期
        try {
            JWTValidator.of(token).validateDate(DateUtil.date());
        } catch (Exception e) {
            //进入异常就是token过期了
            return false;
        }
        return true;
    }

    @Override
    public Result CheckToken(String token, String refreshToken) {
        Map<String, Object> resultMap = new HashMap<>();
        //验证token是否合法
        Boolean verify = this.verify(token);
        //如果合法就返回true
        if (verify) {
            return Result.ok().data(resultMap);
        }
        //如果不合法就验证refreshToken
        Boolean refreshVerify = this.verify(refreshToken);
        //如果合法
        if (refreshVerify) {
            //判断refreshToken中的ID是否和token中的ID一样
            JWT jwt = JWTUtil.parseToken(token);
            JWT refreshJwt = JWTUtil.parseToken(refreshToken);
            if (!jwt.getPayload("ID").equals(refreshJwt.getPayload("ID"))) {
                return Result.fail(ResultEnum.LOGIN_EXPIRES);
            }
            //根据refreshToken生成新Token 
            String createTokenByRefreshToken = this.createTokenByRefreshToken(refreshJwt);

            //判断refreshToken过期时间是否小于24小时
            //变成13位
            Long exp = Long.parseLong(refreshJwt.getPayload("exp").toString()) * 1000;

            if (exp - System.currentTimeMillis() <= 60 * 60 * 24) {
//                System.out.println("小于24小时");
                //小于24小时重新生成一个 refreshToken
                String refresh = this.refresh(refreshJwt);
                resultMap.put("token", createTokenByRefreshToken);
                resultMap.put("refreshToken", refresh);
                return Result.ok().data(resultMap);
            } else {
                //大于就把原来的返回去
                resultMap.put("token", createTokenByRefreshToken);
                resultMap.put("refreshToken", refreshToken);
                return Result.ok().data(resultMap);
            }

        }
        return Result.fail(ResultEnum.LOGIN_EXPIRES);
    }
}
