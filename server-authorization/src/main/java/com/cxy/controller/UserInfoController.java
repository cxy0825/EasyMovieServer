package com.cxy.controller;

import com.cxy.service.UserInfoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用户表的拓展信息字段(UserInfo)表控制层
 *
 * @author makejava
 * @since 2022-11-28 13:29:58
 */
@RestController
@RequestMapping("userInfo")
public class UserInfoController {
    /**
     * 服务对象
     */
    @Resource
    private UserInfoService userInfoService;

}

