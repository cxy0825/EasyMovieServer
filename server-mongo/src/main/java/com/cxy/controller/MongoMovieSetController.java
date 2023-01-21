package com.cxy.controller;

import com.cxy.entry.MovieSet;
import com.mongodb.client.result.UpdateResult;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("mongo/movieSet")
public class MongoMovieSetController {
    @Resource
    MongoTemplate mongoTemplate;

    //添加一条记录
    @PostMapping("/add")
    public boolean addMovieSet(@RequestBody MovieSet movieSet) {
        //mongo存入的时候会按照0时区去存所以要+8才能变成东八区
//        movieSet.setMovieStartTime(movieSet.getMovieStartTime().plusHours(8));
//        movieSet.setMovieEndTime(movieSet.getMovieEndTime().plusHours(8));
        MovieSet insert = mongoTemplate.insert(movieSet);

        return true;
    }

    //根据ID获取记录
    @GetMapping("/getInfo/{ID}")
    public MovieSet getMovieSetByID(@PathVariable("ID") Long ID) {
        MovieSet movieSet = mongoTemplate.findById(ID, MovieSet.class);
        return movieSet;
    }

    //根据ID修改mongo中的位置信息
    @PostMapping("/updata")
    public Boolean updata(@RequestBody MovieSet movieSet) {
        //先查询有没有记录
        MovieSet movieSet1 = mongoTemplate.findById(movieSet.getId(), MovieSet.class);
        if (null == movieSet1) {
            return true;
        }
        //有记录就修改
        Update update = new Update();
        update.set("movieStartTime", movieSet.getMovieStartTime());
        update.set("movieEndTime", movieSet.getMovieEndTime());
        UpdateResult updateResult = mongoTemplate.updateFirst(
                Query.query(Criteria.where("_id").is(movieSet.getId())),
                update,
                "em_movieSet"
        );
        if (updateResult.getModifiedCount() >= 1) {
            return true;
        }
        return false;
    }

    //获取某个电影院今天的排片情况
    @GetMapping("/todayInfo/{cinemaID}/{filmID}")
    public List<MovieSet> getTodayInfo(
            @PathVariable("cinemaID") Long cinemaID,
            @PathVariable("filmID") Long filmID) {

        Criteria criteria = new Criteria();
        criteria.andOperator(
                Criteria.where("cinemaId").is(cinemaID),
                Criteria.where("filmId").is(filmID),
//                Criteria.where("movieStartTime").lt(LocalDateTime.now().withHour(23).withMinute(59)),
                Criteria.where("movieStartTime").gt(LocalDateTime.now()),
                Criteria.where("movieStartTime").lt(LocalDateTime.now().with(LocalTime.MAX))
//                Criteria.where("movieStartTime").lt(endTime)
        );


        Query query = new Query(criteria);
        List<MovieSet> movieSets = mongoTemplate.find(query, MovieSet.class);
        return movieSets;
    }

    //根据ID添加座位
    @PostMapping("/addSeat/{movieSetId}")
    public void addSeat(
            @PathVariable("movieSetId") Long movieSetId,
            @RequestBody Integer[][] buyArrs) {
        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("_id").is(movieSetId));
        Query query = new Query(criteria);
        Update update = new Update();
        Update.AddToSetBuilder ab = update.new AddToSetBuilder("seatInfo");
        ab.each(buyArrs);
        mongoTemplate.upsert(query, update, MovieSet.class);
    }
}
