package com.cxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cxy.entry.Cinema;

/**
 * @author Cccxy
 * @description 针对表【cinema(电影院信息表)】的数据库操作Service
 * @createDate 2022-11-29 15:48:00
 */
public interface CinemaService extends IService<Cinema> {

    boolean add(Cinema cinema);
}
