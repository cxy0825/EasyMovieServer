package com.cxy.service.impl;

import com.cxy.entry.mongoEntry.MongoCinema;
import com.cxy.service.MongoCinemaService;
import com.mongodb.client.result.DeleteResult;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MongoCinemaImpl implements MongoCinemaService {
    @Resource
    MongoTemplate mongoTemplate;

    @Override
    public boolean add(MongoCinema mongoCinema) {
        try {
            mongoTemplate.insert(mongoCinema);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<MongoCinema> info(double x, double y, int page, int limit) {

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.geoNear(
                        NearQuery.near(y, x) //经纬度
                                .spherical(true)    //是否采用球面几何计算
                                .distanceMultiplier(6378D), "distance"),
                Aggregation.skip(page - 1),
                Aggregation.limit(limit)
        ); //是别名distance
        List<MongoCinema> list = mongoTemplate.aggregate(aggregation, "em_cinema", MongoCinema.class).getMappedResults();
//        System.out.println(list);
        return list;
    }

    @Override
    public boolean del(Long id) {

        Query query = new Query(Criteria.where("_id").is(id));
        DeleteResult remove = mongoTemplate.remove(query, MongoCinema.class);
        if (remove.getDeletedCount() >= 1) {
            return true;
        }
        return false;
    }
}
