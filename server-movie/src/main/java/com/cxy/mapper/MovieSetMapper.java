package com.cxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxy.Dto.MovieSetDto;
import com.cxy.entry.MovieSet;
import org.apache.ibatis.annotations.Param;

/**
 * @author Cccxy
 * @description 针对表【movie_set(排片信息)】的数据库操作Mapper
 * @createDate 2022-11-29 15:48:00
 * @Entity com.cxy.entry.MovieSet
 */
public interface MovieSetMapper extends BaseMapper<MovieSet> {
    MovieSetDto getMovieSetInfoById(Long ID);

    Page<MovieSetDto> getMovieSetInfo(Page<MovieSetDto> page);

    Page<MovieSetDto> getMovieSetInfoByFilmName(Page<MovieSetDto> page, @Param("name") String name);
}




