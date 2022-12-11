package com.cxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxy.clients.mongo.MongoClient;
import com.cxy.entry.TagIndex;
import com.cxy.mapper.TagIndexMapper;
import com.cxy.result.Result;
import com.cxy.service.TagIndexService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Cccxy
 * @description 针对表【tag_index(电影和标签关系表)】的数据库操作Service实现
 * @createDate 2022-11-29 15:48:00
 */
@Service
public class TagIndexServiceImpl extends ServiceImpl<TagIndexMapper, TagIndex>
        implements TagIndexService {
    @Resource
    MongoClient mongoClient;

    @Override
    public Result deleteTagWithFilmID(Long filmID, Long tagID) {
        LambdaUpdateWrapper<TagIndex> qw = new LambdaUpdateWrapper<>();
        qw.eq(TagIndex::getFilmId, filmID);
        qw.eq(TagIndex::getTagId, tagID);
        baseMapper.delete(qw);
        //删除mongo缓存
        mongoClient.deleteFilmInfoById(filmID);
        return Result.ok();
    }

    @Override
    public Result addTagWithFilmID(Long filmID, Long tagID) {
        TagIndex tagIndex = new TagIndex();
        tagIndex.setFilmId(filmID);
        tagIndex.setTagId(tagID);
        LambdaQueryWrapper<TagIndex> qw = new LambdaQueryWrapper<>();
        qw.eq(TagIndex::getFilmId, filmID);
        qw.eq(TagIndex::getTagId, tagID);
        Integer count = baseMapper.selectCount(qw);
        if (count > 0) {
            return Result.fail().message("不能重复插入");
        }

        int insert = baseMapper.insert(tagIndex);
        //删除mongo缓存
        mongoClient.deleteFilmInfoById(filmID);

        return Result.ok().message("插入成功");
    }
}




