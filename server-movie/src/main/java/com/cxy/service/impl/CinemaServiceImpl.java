package com.cxy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxy.clients.mongo.MongoClient;
import com.cxy.entry.Cinema;
import com.cxy.entry.mongoEntry.MongoCinema;
import com.cxy.mapper.CinemaMapper;
import com.cxy.result.Result;
import com.cxy.service.CinemaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Cccxy
 * @description 针对表【cinema(电影院信息表)】的数据库操作Service实现
 * @createDate 2022-11-29 15:48:00
 */
@Service
public class CinemaServiceImpl extends ServiceImpl<CinemaMapper, Cinema>
        implements CinemaService {
    @Resource
    MongoClient mongoClient;

    @Override
    public boolean add(Cinema cinema) {
        System.out.println("aaa");
        //id不为空表示修改
        if (cinema.getId() != null) {

        } else {//id为空表示添加
            //先加入到数据库
            int insert = baseMapper.insert(cinema);
            if (insert < 1) {
                return false;
            }
            //再去mongo中添加
            MongoCinema mongoCinema = BeanUtil.copyProperties(cinema, MongoCinema.class);
            double x = cinema.getX();
            double y = cinema.getY();
            //添加经纬度
            mongoCinema.setJw(new double[]{y, x});
            boolean add = mongoClient.add(mongoCinema);
            if (add) {
                return true;
            }
        }


        return false;
    }

    @Override
    public Cinema getInfo(Long id) {
        //先去mongo中查询改电影院信息
        Result cinemaInfo = mongoClient.getCinemaInfo(id);
        if (cinemaInfo.getData() != null) {

            String s = JSONUtil.toJsonStr(cinemaInfo.getData());
            Cinema cinema = JSONUtil.toBean(s, Cinema.class);
            return cinema;
        }
        //否则去数据库查找
        Cinema cinema = baseMapper.selectById(id);
        return cinema;
    }
}




