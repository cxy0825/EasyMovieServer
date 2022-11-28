package com.cxy.controller;

import com.cxy.service.OauthsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 第三方信息用户表(Oauths)表控制层
 *
 * @author makejava
 * @since 2022-11-28 13:29:56
 */
@RestController
@RequestMapping("oauths")
public class OauthsController {
    /**
     * 服务对象
     */
    @Resource
    private OauthsService oauthsService;


}

