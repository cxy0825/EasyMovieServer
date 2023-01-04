package com.cxy.service.impl;

import com.cxy.entry.mongoEntry.MongoCinema;
import com.cxy.entry.mongoEntry.MongoFilmInfo;
import com.cxy.service.MongoCinemaService;
import com.mongodb.client.result.DeleteResult;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<MongoCinema> info(double x, double y, int page, int limit, String sort) {
        //排序
        Sort.Direction desc = Sort.Direction.DESC;
        SortOperation distance = Aggregation.sort(Sort.Direction.ASC, "distance");
        if (sort.equals("desc")) {
            distance = Aggregation.sort(Sort.Direction.DESC, "distance");
        }


        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.geoNear(
                        NearQuery.near(y, x) //经纬度
                                .spherical(true)    //是否采用球面几何计算
                                .distanceMultiplier(6378D), "distance"),
                distance,
                Aggregation.skip((page - 1) * limit),
                Aggregation.limit(limit)

        ); //是别名distance
        List<MongoCinema> list = mongoTemplate.aggregate(aggregation, "em_cinema", MongoCinema.class)
                .getMappedResults();

//        System.out.println(list);
        return list;
    }

    @Override
    public List<MongoCinema> infoByFilmID(double x, double y, int page, int limit, String sort, String filmName) {
        //先查询出那些电影院有这场电影
        Query query = new Query(Criteria.where("filmName").regex(filmName));

        List<Long> cinemaIdList =
                mongoTemplate.find(query, MongoFilmInfo.class)
                        .stream().map(item -> {
                            return item.getCinemaId();
                        }).collect(Collectors.toList());
        if (cinemaIdList.size() == 0) {
            return new ArrayList<MongoCinema>();
        }
        System.out.println(cinemaIdList);
        //排序
        Sort.Direction desc = Sort.Direction.DESC;
        SortOperation distance = Aggregation.sort(Sort.Direction.ASC, "distance");
        if (sort.equals("desc")) {
            distance = Aggregation.sort(Sort.Direction.DESC, "distance");
        }

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.geoNear(
                        NearQuery.near(y, x) //经纬度
                                .spherical(true)    //是否采用球面几何计算
                                .distanceMultiplier(6378D), "distance"),
                distance,
                Aggregation.match(Criteria.where("_id").in(cinemaIdList)),
                Aggregation.skip((page - 1) * limit),
                Aggregation.limit(limit)

        ); //是别名distance
        List<MongoCinema> list = mongoTemplate.aggregate(aggregation, "em_cinema", MongoCinema.class)
                .getMappedResults();

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
