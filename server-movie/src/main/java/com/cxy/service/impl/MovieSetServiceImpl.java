package com.cxy.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxy.Dto.MovieSetDto;
import com.cxy.entry.Film;
import com.cxy.entry.MovieSet;
import com.cxy.mapper.MovieSetMapper;
import com.cxy.result.Result;
import com.cxy.service.FilmService;
import com.cxy.service.MovieSetService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * @author Cccxy
 * @description 针对表【movie_set(排片信息)】的数据库操作Service实现
 * @createDate 2022-11-29 15:48:00
 */
@Service
public class MovieSetServiceImpl extends ServiceImpl<MovieSetMapper, MovieSet>
        implements MovieSetService {
    @Resource
    FilmService filmService;

    @Override
    public MovieSetDto getMovieSetInfoById(Long ID) {
        //先从mongo中查找

        //如果mongo中没有就去数据库查找
        return baseMapper.getMovieSetInfoById(ID);
    }

    @Override
    public Page<MovieSetDto> getMovieSetInfo(Page<MovieSetDto> movieSetPage) {

        return baseMapper.getMovieSetInfo(movieSetPage);

    }

    @Override
    public Page<MovieSetDto> getMovieSetInfoByFilmName(Page<MovieSetDto> movieSetPage, String name) {

        return baseMapper.getMovieSetInfoByFilmName(movieSetPage, name);
    }

    @Override
    public Result updateMovieSetInfo(MovieSet movieSet) {
        //根据filmId获取电影的时长
        Long filmId = movieSet.getFilmId();

        Film film = filmService.getById(filmId);

        Double duration = film.getDuration();
        //开始时间+电影时长 = 结束时间
        LocalDateTime movieStartTime = movieSet.getMovieStartTime();
        //计算出结束时间
        LocalDateTime movieEndTime = movieStartTime.plus(duration.longValue(), ChronoUnit.MINUTES);
        return null;
    }
}




