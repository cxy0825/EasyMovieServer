package com.cxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cxy.entry.Performer;
import com.cxy.result.Result;

/**
 * @author Cccxy
 * @description 针对表【performer(演员信息表)】的数据库操作Service
 * @createDate 2022-12-27 20:44:41
 */
public interface PerformerService extends IService<Performer> {

    Result getInfo(Long ID);

}
