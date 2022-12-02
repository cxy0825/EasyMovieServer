package com.cxy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.jwt.JWTPayload;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxy.Utils.ThreadLocalUtil;
import com.cxy.clients.mongo.MongoClient;
import com.cxy.entry.Cinema;
import com.cxy.entry.Moviehouse;
import com.cxy.entry.mongoEntry.MongoMoviehouse;
import com.cxy.mapper.MoviehouseMapper;
import com.cxy.result.Result;
import com.cxy.result.ResultEnum;
import com.cxy.service.CinemaService;
import com.cxy.service.MoviehouseService;
import com.cxy.vo.resultVo.MovieHouseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Cccxy
 * @description 针对表【moviehouse】的数据库操作Service实现
 * @createDate 2022-11-29 15:48:00
 */
@Service
public class MoviehouseServiceImpl extends ServiceImpl<MoviehouseMapper, Moviehouse>
        implements MoviehouseService {
    @Resource
    CinemaService cinemaService;
    @Resource
    MongoClient mongoClient;

    @Override
    public Result getMovieHouseWithCinema(Integer page, Integer limit, String name) {
        //根据身份进行判断 如果管理员type是"admin"就按照里面的ID进行查询
        //如果是 "root"就默认查询全部
        JWTPayload jwtPayload = (JWTPayload) ThreadLocalUtil.get();
        LambdaQueryWrapper<Moviehouse> queryWrapper = new LambdaQueryWrapper<>();
//        如果不是超级管理员就根据ID进行查询
        if (!jwtPayload.getClaim("type").equals("root")) {
            queryWrapper.eq(Moviehouse::getCinemaId, jwtPayload.getClaim("cinemaID"));
        }
        //跟上查询条件
        queryWrapper.eq(!StringUtils.isEmpty(name), Moviehouse::getMovieHouseName, name);

        Page<Moviehouse> movieHousePage = new Page<>(page, limit);

        baseMapper.selectPage(movieHousePage, queryWrapper);
//        根据cinemaID查询对应的电影院信息
        Page<MovieHouseVo> movieHouseVoPage = new Page<>();
        BeanUtils.copyProperties(movieHousePage, movieHouseVoPage);


        List<MovieHouseVo> collect = movieHousePage.getRecords().stream().map(item -> {
            //转成vo对象
            MovieHouseVo movieHouseVo = new MovieHouseVo();
            BeanUtils.copyProperties(item, movieHouseVo);

//            根据cinemaID查询对应的电影院信息
            Cinema byId = cinemaService.getById(item.getCinemaId());

            movieHouseVo.setCinema(byId);
            return movieHouseVo;
        }).collect(Collectors.toList());
        movieHouseVoPage.setRecords(collect);
        return Result.ok().data(movieHouseVoPage);
    }

    @Override
    @Transactional
    public Result add(Moviehouse moviehouse) {
        //判断当前传进来的movie中的cinemaID是否和token中的
        Boolean root = isRoot();
        boolean update = false;
        if (root) {
            update = this.saveOrUpdate(moviehouse);
        }
        //否则吧cinemaID设置成token中一样
        JWTPayload jwtPayload = (JWTPayload) ThreadLocalUtil.get();
        Long cinemaID = Long.valueOf(String.valueOf(jwtPayload.getClaim("cinemaID")));
        moviehouse.setCinemaId(cinemaID);
        update = this.saveOrUpdate(moviehouse);
        //只有成功后才返回
        if (update) {
            return Result.ok();
        }
        return Result.fail().message("更新或者插入失败");
    }

    @Override
    @Transactional
    public Result delete(Long id) {
        int i = baseMapper.deleteById(id);
        if (i >= 1) {
            return Result.ok();
        }
        return Result.fail().message("删除失败");
    }

    @Override
    public Result getMovieHouseInfo(Long movieHouseID) {

        //远程调用
        //通过movieHouseID获取mongo中的播放厅座位详情
        Result movieHouseById = mongoClient.getMovieHouseById(movieHouseID);
        Object data = movieHouseById.getData();
        //查到数据就返回
        if (data != null) {
            return Result.ok().data(data);
        }
        //没查到就去查看数据库

        //查询数据库

        Moviehouse moviehouse = baseMapper.selectById(movieHouseID);
        MongoMoviehouse mongoMoviehouse = null;
        //如果数据库中查询出为空 也添加到mongo中,防止缓存穿透
        if (moviehouse == null) {
            mongoMoviehouse = new MongoMoviehouse();
            mongoMoviehouse.setId(movieHouseID);
        } else {
            mongoMoviehouse = BeanUtil.copyProperties(moviehouse, MongoMoviehouse.class);
            //把有多少行多少列添加到稀疏数组的第0项
            int[][] arr = new int[1][3];
            arr[0][0] = moviehouse.getRowNum();
            arr[0][1] = moviehouse.getColNum();
            arr[0][2] = 0;
            mongoMoviehouse.setSeatInfo(arr);
        }
        //添加到mongo中
        Result result = mongoClient.insertMovieHouse(mongoMoviehouse);

        if (result.getCode() == 20000) {
            return Result.ok().data(moviehouse);
        }
        return Result.fail();
    }

    @Override
    public Result updateSeatById(Long movieHouseID, HashMap<String, int[]> map) {
        int[] arrs = new int[3];
        try {
            arrs = map.get("arr");
        } catch (Exception e) {
            return Result.fail(ResultEnum.ERROR_PARAMS);
        }
        //远程调用mongo服务
        return mongoClient.updateSeatById(movieHouseID, arrs);

    }

    //判断当前用户是否为超级管理员
    public Boolean isRoot() {
        JWTPayload jwtPayload = (JWTPayload) ThreadLocalUtil.get();

        String type = (String) jwtPayload.getClaim("type");
        if (type.equals("root")) {
            return true;
        }
        return false;
    }
}




