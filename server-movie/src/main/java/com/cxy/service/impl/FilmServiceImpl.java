package com.cxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxy.clients.mongo.MongoClient;
import com.cxy.entry.Cinema;
import com.cxy.entry.Film;
import com.cxy.entry.FilmInfo;
import com.cxy.mapper.FilmMapper;
import com.cxy.mapper.TagIndexMapper;
import com.cxy.result.Result;
import com.cxy.service.CinemaService;
import com.cxy.service.FilmInfoService;
import com.cxy.service.FilmService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

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
    @Resource
    CinemaService cinemaService;
    @Resource
    FilmInfoService filmInfoService;
    @Resource
    TagIndexMapper tagIndexMapper;

    @Override
    public Result getFilmInfoByID(Long ID) {
        //先去查询mongo中有没有
        Result filmInfoByID = mongoClient.getFilmInfoByID(ID);
        if (filmInfoByID.getData() != null) {

            return filmInfoByID;
        }
        //再去查数据库
        Film film = baseMapper.selectById(ID);
        //查出电影院名字
        Cinema cinema = cinemaService.getById(film.getCinemaId());
        film.setCinemaName(cinema.getCinemaName());

        //存到mongo中
        mongoClient.insertFilmInfo(film);

        //查出电影的海报和视频
        LambdaQueryWrapper<FilmInfo> filmInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        filmInfoLambdaQueryWrapper.eq(FilmInfo::getFilmId, film.getId());
        FilmInfo filmInfo = filmInfoService.getOne(filmInfoLambdaQueryWrapper);
        if (filmInfo != null) {
            Set<String> posterUrl = new HashSet<>();
            posterUrl.add(filmInfo.getPosterUrl());
            film.setPosterUrls(posterUrl);
            Set<String> videoUrl = new HashSet<>();
            videoUrl.add(filmInfo.getVideoUrl());
            film.setVideoUrls(videoUrl);
        }

        //查询标签
        HashSet<String> tags = tagIndexMapper.selectTagsByID(ID);
        film.setTags(tags);
        //返回结果
        return Result.ok().data(film);

    }


    @Override
    public Result getFilmInfoList(Page<Film> filmPage, Long cinemaID, String name) {

        LambdaQueryWrapper<Film> filmLambdaQueryWrapper = new LambdaQueryWrapper<>();
        filmLambdaQueryWrapper.eq(cinemaID != 0, Film::getCinemaId, cinemaID);
        filmLambdaQueryWrapper.like(!name.equals("-1"), Film::getFilmName, name);
        Page<Film> filmInfoByName = baseMapper.selectPage(filmPage, filmLambdaQueryWrapper);

        filmInfoByName.getRecords().forEach(item -> {
            //查出电影院名字
            Cinema cinema = cinemaService.getById(cinemaID);
            item.setCinemaName(cinema.getCinemaName());
            //查出电影的海报和视频
            LambdaQueryWrapper<FilmInfo> filmInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
            filmInfoLambdaQueryWrapper.eq(FilmInfo::getFilmId, item.getId());
            FilmInfo filmInfo = filmInfoService.getOne(filmInfoLambdaQueryWrapper);
            if (filmInfo != null) {

                Set<String> posterUrl = new HashSet<>();
                posterUrl.add(filmInfo.getPosterUrl());
                item.setPosterUrls(posterUrl);
                Set<String> videoUrl = new HashSet<>();
                videoUrl.add(filmInfo.getVideoUrl());
                item.setVideoUrls(videoUrl);
            }

            //查询标签
            HashSet<String> tags = tagIndexMapper.selectTagsByID(item.getId());
            item.setTags(tags);

        });
        return Result.ok().data(filmInfoByName);
    }


}




