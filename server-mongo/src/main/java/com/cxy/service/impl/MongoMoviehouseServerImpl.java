package com.cxy.service.impl;

import com.cxy.entry.mongoEntry.MongoMoviehouse;
import com.cxy.result.Result;
import com.cxy.service.MongoMoviehouseServer;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

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
    public Result insertSeatByID(Long movieHouseID, int[] arr) {

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

    @Override
    public Result updateSeatByID(Long movieHouseID, Integer[] arr) {

        //执行三次,把状态0,1,2的全部更新成传递进来的值
        for (int i = 0; i < 3; i++) {
            ArrayList<Integer[]> integers = new ArrayList<>();
            integers.add(new Integer[]{arr[0], arr[1], i});
            Query query = new Query();
            query.addCriteria(Criteria.where("id").is(movieHouseID));
            query.addCriteria(
                    Criteria.where("seatInfo").all(integers)

            );

            Update update = new Update();
            update.set("seatInfo.$", new Integer[]{arr[0], arr[1], arr[2]});
            UpdateResult updateResult = mongoTemplate.updateFirst(query, update, MongoMoviehouse.class);
        }

        return Result.ok();
    }

    @Override
    public Result delMovieHouseById(Long movieHouseID) {

        Query query = new Query(Criteria.where("_id").is(movieHouseID));
        DeleteResult remove = mongoTemplate.remove(query, MongoMoviehouse.class);
        if (remove.getDeletedCount() >= 1) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }


}
