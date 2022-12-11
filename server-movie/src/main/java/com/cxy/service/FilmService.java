package com.cxy.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cxy.entry.Film;
import com.cxy.result.Result;

/**
 * @author Cccxy
 * @description 针对表【film(电影影片信息)】的数据库操作Service
 * @createDate 2022-11-29 15:48:00
 */
public interface FilmService extends IService<Film> {


    Result getFilmInfoByID(Long ID);


    Result getFilmInfoList(Page<Film> filmPage, Long cinemaID, String name);

    Result doSaveOrUpdate(Film film);
}
