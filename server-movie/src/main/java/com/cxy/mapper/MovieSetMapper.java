package com.cxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxy.Dto.MovieSetDto;
import com.cxy.entry.MovieSet;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

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

    //检查时间有没有重叠
    //传入修改后的电影开始时间和结束时间
    //判断开始时间或者结束时间有没有在已经安排好的时间段中
    Integer checkTime(
            @Param("movieStartTime") LocalDateTime movieStartTime,
            @Param("movieEndTime") LocalDateTime movieEndTime,
            @Param("movieHouseID") Long movieHouseID);
}




