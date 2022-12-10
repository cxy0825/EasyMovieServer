package com.cxy.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cxy.entry.Film;
import com.cxy.result.Result;
import org.apache.ibatis.annotations.Param;

/**
 * @author Cccxy
 * @description 针对表【film(电影影片信息)】的数据库操作Service
 * @createDate 2022-11-29 15:48:00
 */
public interface FilmService extends IService<Film> {

    Film getFilmInfo(Long ID);


    Result getFilmInfoByID(Long ID);

    Page<Film> getFilmInfoByName(Page<Film> page, @Param("cinemaID") Long cinemaID, @Param("name") String name);

}
