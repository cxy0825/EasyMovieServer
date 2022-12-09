package com.cxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cxy.entry.Film;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Cccxy
 * @description 针对表【film(电影影片信息)】的数据库操作Mapper
 * @createDate 2022-11-29 15:48:00
 * @Entity com.cxy.entry.Film
 */
public interface FilmMapper extends BaseMapper<Film> {
    Film getFilmInfo(@Param("ID") Long ID);

    List<Film> getFilmInfoByName(@Param("name") String name);
}




