package com.cxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cxy.entry.User;
import com.cxy.entry.vo.param.LoginVo;
import com.cxy.result.Result;

/**
 * @author Cccxy
 * @description 针对表【user(用户基本信息表)】的数据库操作Service
 * @createDate 2022-11-28 13:26:52
 */
public interface UserService extends IService<User> {

    Result userLogin(LoginVo loginVo);

    Result adminLogin(LoginVo loginVo);

    Result getAdminInfo(String token);
}
