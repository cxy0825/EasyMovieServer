package com.cxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cxy.entry.Film;
import com.cxy.entry.Performer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Cccxy
 * @description 针对表【performer(演员信息表)】的数据库操作Mapper
 * @createDate 2022-12-27 20:44:41
 * @Entity com.cxy.entry.Performer
 */
public interface PerformerMapper extends BaseMapper<Performer> {
    //根据电影ID查询该电影下的演员
    List<Performer> getPerformersByFilmID(@Param("filmID") Long filmID);

    //根据演员ID查询演员演出过的电影
    List<Film> getPerformByID(@Param("performID") Long performID);
}




