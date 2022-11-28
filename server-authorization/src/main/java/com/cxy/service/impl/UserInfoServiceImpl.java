package com.cxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxy.entry.UserInfo;
import com.cxy.service.UserInfoService;
import com.cxy.mapper.UserInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author Cccxy
* @description 针对表【user_info(用户表的拓展信息字段)】的数据库操作Service实现
* @createDate 2022-11-28 13:26:52
*/
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
    implements UserInfoService{

}




