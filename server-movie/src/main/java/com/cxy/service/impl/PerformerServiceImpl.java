package com.cxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxy.entry.Performer;
import com.cxy.entry.PerformerIndex;
import com.cxy.mapper.PerformerMapper;
import com.cxy.result.Result;
import com.cxy.service.FilmService;
import com.cxy.service.PerformerIndexService;
import com.cxy.service.PerformerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Cccxy
 * @description 针对表【performer(演员信息表)】的数据库操作Service实现
 * @createDate 2022-12-27 20:44:41
 */
@Service
public class PerformerServiceImpl extends ServiceImpl<PerformerMapper, Performer>
        implements PerformerService {
    @Resource
    PerformerIndexService performerIndexService;
    @Resource
    FilmService filmService;

    @Override
    public Result getInfo(Long ID) {
        List<Performer> performersByFilmID = baseMapper.getPerformersByFilmID(ID);
        return Result.ok().data(performersByFilmID);
    }

    @Override
    public Result getInfoByID(Long ID) {

        //根据ID查演员信息
        Performer data = baseMapper.selectById(ID);
        return Result.ok().data(data);
    }

    @Override
    public Result getPerformByID(Long ID) {
        //根据演员ID查询出演过电影的ID
        LambdaQueryWrapper<PerformerIndex> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PerformerIndex::getPerformerId, ID);
        List<PerformerIndex> list = performerIndexService.list(queryWrapper);
        //去film服务中根据id查询出对应的电影信息
        List<Object> filmList = list.stream().map(item -> {
            Result filmInfoByID = filmService.getFilmInfoByID(item.getFilmId());
            return filmInfoByID.getData();
        }).collect(Collectors.toList());

        return Result.ok().data(filmList);
    }

    @Override
    public Page<Performer> getInfoList(Page<Performer> performerPage, Performer performer) {
        LambdaQueryWrapper<Performer> queryWrapper = new LambdaQueryWrapper<>();
        if (null != performer && null != performer.getPerformerName()) {
            queryWrapper.like(Performer::getPerformerName, performer.getPerformerName());
        }
        baseMapper.selectPage(performerPage, queryWrapper);
        return performerPage;
    }


}




