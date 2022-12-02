package com.cxy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxy.entry.Administrator;
import com.cxy.entry.vo.param.AdministratorParam;
import com.cxy.entry.vo.result.AdministratorLoginVo;
import com.cxy.mapper.AdministratorMapper;
import com.cxy.result.Result;
import com.cxy.result.ResultEnum;
import com.cxy.service.AdministratorService;
import com.cxy.service.AuthorizationService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Cccxy
 * @description 针对表【administrator】的数据库操作Service实现
 * @createDate 2022-12-02 16:23:22
 */
@Service
public class AdministratorServiceImpl extends ServiceImpl<AdministratorMapper, Administrator>
        implements AdministratorService {
    @Resource
    AuthorizationService authorizationService;

    @Override
    public Result login(AdministratorParam administratorParam) {
        String account = administratorParam.getAccount();//账号
        String password = administratorParam.getPassword();//密码
        String type = administratorParam.getType();//登录方式
//        判断是否为空
        if (StringUtils.isEmpty(account)) {
            return Result.fail(ResultEnum.MISS_PARAMS);
        }
        if (StringUtils.isEmpty(password)) {
            return Result.fail(ResultEnum.MISS_PARAMS);
        }

        LambdaQueryWrapper<Administrator> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(Administrator::getAccount, account);
        userLambdaQueryWrapper.eq(Administrator::getPassword, password);
        Administrator administrator = baseMapper.selectOne(userLambdaQueryWrapper);
        if (administrator == null) {
            return Result.fail(ResultEnum.MISS_USER);
        }
        AdministratorLoginVo administratorLoginVo = new AdministratorLoginVo();
        //登录成功后去授权服务生成token
        administratorLoginVo.setToken(authorizationService.createToken(administrator));

        BeanUtil.copyProperties(administrator, administratorLoginVo);

        return Result.ok().data(administratorLoginVo);
    }
}




