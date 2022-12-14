package com.cxy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxy.clients.mongo.RedisClient;
import com.cxy.entry.Administrator;
import com.cxy.entry.Token;
import com.cxy.entry.User;
import com.cxy.entry.vo.param.LoginVo;
import com.cxy.mapper.UserMapper;
import com.cxy.result.Result;
import com.cxy.result.ResultEnum;
import com.cxy.service.AdministratorService;
import com.cxy.service.AuthorizationService;
import com.cxy.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Cccxy
 * @description 针对表【user(用户基本信息表)】的数据库操作Service实现
 * @createDate 2022-11-28 13:26:52
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Resource
    AuthorizationService authorizationService;
    @Resource
    AdministratorService administratorService;
    @Resource
    RedisClient redisClient;

    @Override
    public Result userLogin(LoginVo loginVo) {
        String account = loginVo.getAccount();//账号
        String password = loginVo.getPassword();//密码
        String type = loginVo.getType();//登录方式
//        判断是否为空
        if (StringUtils.isEmpty(account)) {
            return Result.fail(ResultEnum.MISS_PARAMS);
        }
        if (StringUtils.isEmpty(password)) {
            return Result.fail(ResultEnum.MISS_PARAMS);
        }
        if (StringUtils.isEmpty(type)) {
            return Result.fail(ResultEnum.MISS_PARAMS);
        }

        //如果 type==ez是官网账号登录
        // 如果 type==ali 是支付宝登录
        User user = null;
        if (type.equals("em")) {
            LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
            userLambdaQueryWrapper.eq(User::getAccount, account);
            userLambdaQueryWrapper.eq(User::getPassword, password);
            user = baseMapper.selectOne(userLambdaQueryWrapper);

            if (user == null) {
                return Result.fail(ResultEnum.MISS_USER);
            }
//            System.out.println(user);
        } else {
            return Result.fail(ResultEnum.ERROR_PARAMS);
        }


        return Result.ok();

    }

    @Override
    public Result adminLogin(LoginVo loginVo) {
        String account = loginVo.getAccount();//账号
        String password = loginVo.getPassword();//密码
        //判断是否为空
        if (StringUtils.isEmpty(account)) {
            return Result.fail(ResultEnum.MISS_PARAMS);
        }
        if (StringUtils.isEmpty(password)) {
            return Result.fail(ResultEnum.MISS_PARAMS);
        }

        LambdaQueryWrapper<Administrator> administratorLambdaQueryWrapper = new LambdaQueryWrapper<>();
        administratorLambdaQueryWrapper.eq(Administrator::getAccount, account);
        administratorLambdaQueryWrapper.eq(Administrator::getPassword, password);
        Administrator administrator = administratorService.getOne(administratorLambdaQueryWrapper);
        if (administrator == null) {
            return Result.fail(ResultEnum.MISS_USER);
        }

        //去授权中心生成token
        Token token = BeanUtil.copyProperties(administrator, Token.class);
        //管理员的type和name是一样的这里是为了存储方便所以设置type
        token.setType(administrator.getName());

        String data = authorizationService.createToken(token);
        return Result.ok().data(data);
    }

    @Override
    public Result getAdminInfo(String token) {
        //从token中分离签名出来
        String sign = token.substring(token.lastIndexOf(".") + 1);
        try {
            JWT jwt = JWTUtil.parseToken(token);
            String account = (String) jwt.getPayload("account");
            Token userInfo = redisClient.getUserInfo(account, sign);
            return Result.ok().data(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail();
        }


    }

}




