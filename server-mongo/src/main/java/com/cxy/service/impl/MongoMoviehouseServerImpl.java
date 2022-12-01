package com.cxy.service.impl;

import com.cxy.entry.mongoEntry.MongoMoviehouse;
import com.cxy.result.Result;
import com.cxy.service.MongoMoviehouseServer;
import com.mongodb.client.result.UpdateResult;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MongoMoviehouseServerImpl implements MongoMoviehouseServer {
    @Resource
    MongoTemplate mongoTemplate;

    @Override
    public Result getMovieHouseById(Long movieHouseID) {


        Query query = new Query(Criteria.where("id").is(movieHouseID));
        MongoMoviehouse mongoMoviehouse = mongoTemplate.findOne(query, MongoMoviehouse.class);
        if (mongoMoviehouse == null) {
            return Result.ok();
        }
        return Result.ok().data(mongoMoviehouse);
    }

    @Override
    public Result insertData(MongoMoviehouse mongoMoviehouse) {

        mongoTemplate.insert(mongoMoviehouse);
        return Result.ok();
    }

    @Override
    public Result updateSeatById(Long movieHouseID, int[] arr) {

        //把压缩后的稀疏数组存入到mongo中
        Update update = new Update();
        update.addToSet("seatInfo", arr);
        Criteria id = Criteria.where("id").is(movieHouseID);
        Query query = Query.query(id);
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, MongoMoviehouse.class);
        if (updateResult.getModifiedCount() >= 1) {
            return Result.ok();
        }
        return Result.fail().message("更新座位数据失败");
    }


}
