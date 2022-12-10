package com.cxy.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxy.clients.mongo.MongoClient;
import com.cxy.entry.Film;
import com.cxy.mapper.FilmMapper;
import com.cxy.result.Result;
import com.cxy.service.FilmService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Cccxy
 * @description 针对表【film(电影影片信息)】的数据库操作Service实现
 * @createDate 2022-11-29 15:48:00
 */
@Service
public class FilmServiceImpl extends ServiceImpl<FilmMapper, Film>
        implements FilmService {

    @Resource
    MongoClient mongoClient;

    @Override
    public Film getFilmInfo(Long ID) {
        return baseMapper.getFilmInfo(ID);

    }


    @Override
    public Result getFilmInfoByID(Long ID) {
        //先去查询mongo中有没有
        Result filmInfoByID = mongoClient.getFilmInfoByID(ID);
        if (filmInfoByID.getData() != null) {

            return filmInfoByID;
        }
        //再去查数据库
        Film filmInfo = baseMapper.getFilmInfo(ID);
        //存到mongo中
        mongoClient.insertFilmInfo(filmInfo);
        //返回结果
        return Result.ok().data(filmInfo);


    }

    @Override
    public Page<Film> getFilmInfoByName(Page<Film> page, Long cinemaID, String name) {
        return baseMapper.getFilmInfoByName(page, cinemaID, name);


    }

    @Override
    public Result getFilmInfoList(Page<Film> filmPage, Long cinemaID, String name) {

        Page<Film> filmInfoByName = baseMapper.getFilmInfoByName(filmPage, cinemaID, name);

        return Result.ok().data(filmInfoByName);
    }


}




