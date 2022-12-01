package com.cxy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxy.entry.User;
import com.cxy.entry.vo.param.LoginParam;
import com.cxy.entry.vo.result.LoginVo;
import com.cxy.mapper.UserMapper;
import com.cxy.result.Result;
import com.cxy.result.ResultEnum;
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

    @Override
    public Result login(LoginParam loginParam) {
        String phone = loginParam.getPhone();//账号
        String password = loginParam.getPassword();//密码
        String type = loginParam.getType();//登录方式
//        判断是否为空
        if (StringUtils.isEmpty(phone)) {
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
            userLambdaQueryWrapper.eq(User::getPhone, phone);
            userLambdaQueryWrapper.eq(User::getPassword, password);

            user = baseMapper.selectOne(userLambdaQueryWrapper);
            if (user == null) {
                return Result.fail(ResultEnum.MISS_USER);
            }
//            System.out.println(user);
        } else {
            return Result.fail(ResultEnum.ERROR_PARAMS);
        }


        LoginVo loginVo = new LoginVo();
        //登录成功后去授权服务生成token
        loginVo.setTokenMap(authorizationService.createToken(user));
        BeanUtil.copyProperties(user, loginVo);


        return Result.ok().data(loginVo);

    }

}




