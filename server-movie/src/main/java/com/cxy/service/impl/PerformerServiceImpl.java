package com.cxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxy.entry.Performer;
import com.cxy.mapper.PerformerMapper;
import com.cxy.result.Result;
import com.cxy.service.PerformerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Cccxy
 * @description 针对表【performer(演员信息表)】的数据库操作Service实现
 * @createDate 2022-12-27 20:44:41
 */
@Service
public class PerformerServiceImpl extends ServiceImpl<PerformerMapper, Performer>
        implements PerformerService {
    @Resource
    PerformerIndexServiceImpl performerIndexService;

    @Override
    public Result getInfo(Long ID) {
        List<Performer> performersByFilmID = baseMapper.getPerformersByFilmID(ID);
        return Result.ok().data(performersByFilmID);
    }
}




