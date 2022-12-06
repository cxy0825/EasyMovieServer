package com.cxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cxy.entry.Administrator;
import com.cxy.entry.vo.param.AdministratorParam;
import com.cxy.result.Result;

/**
 * @author Cccxy
 * @description 针对表【administrator】的数据库操作Service
 * @createDate 2022-12-02 16:23:22
 */
public interface AdministratorService extends IService<Administrator> {

    Result login(AdministratorParam administratorParam);

    Result getInfo(String token);
}
