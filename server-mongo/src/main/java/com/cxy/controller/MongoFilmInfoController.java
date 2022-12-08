package com.cxy.controller;

import cn.hutool.core.bean.BeanUtil;
import com.cxy.entry.Film;
import com.cxy.entry.mongoEntry.MongoFilmInfo;
import com.cxy.result.Result;
import com.mongodb.client.result.DeleteResult;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("mongo/public")
public class MongoFilmInfoController {
    @Resource
    MongoTemplate mongoTemplate;

    /**
     * 通过id获取电影信息
     *
     * @param ID id
     * @return {@link Result}
     */
    @GetMapping("/film/info/{ID}")
    public Result getFilmInfoByID(@PathVariable Long ID) {
        Query query = new Query(Criteria.where("id").is(ID));
        MongoFilmInfo filmInfo = mongoTemplate.findOne(query, MongoFilmInfo.class);
        return Result.ok().data(filmInfo);
    }

    /**
     * 插入电影信息
     *
     * @return {@link Result}
     */
    @PostMapping("/film/info/insert")
    public Result insertFilmInfo(@RequestBody Film film) {
        //先转成MongoFilmInfo类型
        MongoFilmInfo filmInfo = BeanUtil.copyProperties(film, MongoFilmInfo.class);
        mongoTemplate.insert(filmInfo);
        return Result.ok().data(filmInfo);
    }

    /*
     * 根据ID删除电影信息
     * */
    @PostMapping("/film/info/delete/{ID}")
    public boolean deleteFilmInfoById(@PathVariable("ID") Long ID) {

        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(ID));
        DeleteResult remove = mongoTemplate.remove(query, MongoFilmInfo.class);
        return remove.getDeletedCount() > 0;
    }
}
