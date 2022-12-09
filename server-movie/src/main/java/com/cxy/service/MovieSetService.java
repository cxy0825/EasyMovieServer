package com.cxy.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cxy.Dto.MovieSetDto;
import com.cxy.entry.MovieSet;
import com.cxy.result.Result;

/**
 * @author Cccxy
 * @description 针对表【movie_set(排片信息)】的数据库操作Service
 * @createDate 2022-11-29 15:48:00
 */
public interface MovieSetService extends IService<MovieSet> {

    MovieSetDto getMovieSetInfoById(Long ID);

    Page<MovieSetDto> getMovieSetInfo(Page<MovieSetDto> movieSetPage);

    Page<MovieSetDto> getMovieSetInfoByFilmName(Page<MovieSetDto> movieSetPage, String name);


    Result updateMovieSetInfo(MovieSet movieSet);

    Result addMovieSetInfo(MovieSet movieSet);

    Result getLastMovieInfo(Long movieHouseID);
}
