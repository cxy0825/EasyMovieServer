package com.cxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxy.Dto.MovieSetDto;
import com.cxy.clients.mongo.MongoClient;
import com.cxy.entry.Film;
import com.cxy.entry.MovieSet;
import com.cxy.mapper.MovieSetMapper;
import com.cxy.result.Result;
import com.cxy.result.ResultEnum;
import com.cxy.service.FilmService;
import com.cxy.service.MovieSetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
    @Resource
    MongoClient mongoClient;

    @Override
    public MovieSetDto getMovieSetInfoById(Long ID) {
        //先从mongo中查找

        //如果mongo中没有就去数据库查找
        return baseMapper.getMovieSetInfoById(ID);
    }

    @Override
    public MovieSet getInfo(Long movieSetID) {
        MovieSet movieSet = mongoClient.getMovieSetByID(movieSetID);
        return movieSet;
    }

    @Override
    public Page<MovieSetDto> getMovieSetInfoByFilmName(Page<MovieSetDto> movieSetPage, String name, Long cinemaID) {
//        System.out.println(cinemaID);
        return baseMapper.getMovieSetInfoByFilmName(movieSetPage, name, cinemaID);
    }

    @Override
    @Transactional
    public Result updateMovieSetInfo(MovieSet movieSet) {
        //根据filmId获取电影的时长
        Long filmId = movieSet.getFilmId();

        Film film = filmService.getById(filmId);
        if (film == null) {
            return Result.fail(ResultEnum.ERROR_PARAMS);
        }
        Double duration = film.getDuration();
        //开始时间+电影时长 = 结束时间
        LocalDateTime movieStartTime = movieSet.getMovieStartTime();
        //计算出结束时间
        LocalDateTime movieEndTime = movieStartTime.plusMinutes(duration.longValue());
        movieSet.setMovieEndTime(movieEndTime);
        //修改数据库
        int i = baseMapper.updateById(movieSet);
        if (i > 0) {
            //数据库进行查验
            //查询数据库寻找这个放映厅的时间段包不包含  修改后的时间
            Integer count = baseMapper.checkTime(movieStartTime, movieEndTime, movieSet.getMovieHouseId());

            //修改后,按照开始时间和结束时间查找应该只有一条数据,如果出现数据的条数大于1条就说明电影厅播放电影的时间重叠了,进行事务回滚
            if (count > 1) {
                //播放电影时间重叠,回滚事务
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Result.fail(ResultEnum.TIME_OVERLAP);
            } else {

                //去mongo中删除当前的排片信息,
                Boolean del = mongoClient.updata(movieSet);
                if (del) {
                    return Result.ok();
                } else {
                    //mongo删除失败回滚事务
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.fail().data("缓存更新失败");
                }


            }

        } else {
            return Result.fail().message("数据库出错,数据更新失败");
        }

    }

    @Override
    public Result addMovieSetInfo(MovieSet movieSet) {
        //添加的时候先判断新加进来的排片记录开始时间和结束时间是否和现有的冲突
        //根据filmId获取电影的时长
        Long filmId = movieSet.getFilmId();

        Film film = filmService.getById(filmId);
        if (film == null) {
            return Result.fail(ResultEnum.ERROR_PARAMS);
        }
        Double duration = film.getDuration();
        //开始时间+电影时长 = 结束时间
        LocalDateTime movieStartTime = movieSet.getMovieStartTime();
        //计算出结束时间
        LocalDateTime movieEndTime = movieStartTime.plusMinutes(duration.longValue());
        //查询数据库寻找这个放映厅的时间段包不包含  修改后的时间
        Integer count = baseMapper.checkTime(movieStartTime, movieEndTime, movieSet.getMovieHouseId());
        if (count >= 1) {
            return Result.fail(ResultEnum.TIME_OVERLAP);
        } else {//如果时间没有重叠就添加这条记录
            movieSet.setMovieEndTime(movieEndTime);
            baseMapper.insert(movieSet);
            return Result.ok();
        }

    }

    @Override
    public Result getLastMovieInfo(Long movieHouseID) {
        LambdaQueryWrapper<MovieSet> movieSetLambdaQueryWrapper = new LambdaQueryWrapper<>();
        movieSetLambdaQueryWrapper.eq(MovieSet::getMovieHouseId, movieHouseID);
        movieSetLambdaQueryWrapper.last("limit 1");
        //按照结束时间倒序排列
        movieSetLambdaQueryWrapper.orderByDesc(MovieSet::getMovieEndTime);
        MovieSet one = baseMapper.selectOne(movieSetLambdaQueryWrapper);
        if (one != null) {
            return Result.ok().data(one);
        }
        return Result.fail(ResultEnum.ERROR_PARAMS);
    }

    @Override
    public Result MovieSetInfo(Page<MovieSetDto> movieSetPage, Long cinemaId) {
        //0是最高管理员的cinemaId,数据库sql语句的条件是 cinemaId!=null的时候加入电影院ID约束
        if (cinemaId != null && cinemaId == 0) {
            cinemaId = null;
        }
        Page<MovieSetDto> movieSetInfo = baseMapper.getMovieSetInfo(movieSetPage, cinemaId);
        return Result.ok().data(movieSetInfo);
    }

    @Override
    public List<MovieSet> getTodayInfo(Long cinemaID, Long filmID) {
        //先去mongo中查找没有就去数据库
        List<MovieSet> todayInfo = mongoClient.getTodayInfo(cinemaID, filmID);
        if (todayInfo.size() != 0) {

            return todayInfo;
        }
        //去数据库查找
        LambdaQueryWrapper<MovieSet> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MovieSet::getCinemaId, cinemaID);
        queryWrapper.eq(MovieSet::getFilmId, filmID);
        queryWrapper.gt(MovieSet::getMovieStartTime, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        queryWrapper.lt(MovieSet::getMovieStartTime, LocalDateTime.now().withHour(23).withMinute(59).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        List<MovieSet> movieSets = baseMapper.selectList(queryWrapper);
        //添加到mongo
        movieSets.stream().forEach(item -> {
            mongoClient.addMovieSet(item);
        });

        return movieSets;
    }


}




