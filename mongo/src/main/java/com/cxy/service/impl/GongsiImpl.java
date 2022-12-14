package com.cxy.service.impl;

import com.cxy.common.r;
import com.cxy.entry.Gongsi;
import com.cxy.entry.jijing;
import com.cxy.service.GongsiServer;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GongsiImpl implements GongsiServer {
    @Resource
    MongoTemplate mongoTemplate;

    @Override
    public r getInfo(Long id) {
        Query q = Query.query(Criteria.where("_id").is(id));
        Gongsi one = mongoTemplate.findOne(q, Gongsi.class);
        //根据ID获得发售的基金信息
        List<jijing> jijings = one.getJijing().stream().map(item -> {
            jijing j = mongoTemplate.findOne(
                    Query.query(Criteria.where("_id").is(item)),
                    jijing.class
            );

            return j;
        }).collect(Collectors.toList());
        one.setJijings(jijings);
        return r.ok().data(one);
    }

    @Override
    public r getAll(Integer page, Integer limit) {
        Query query = new Query();

        query.limit(limit).skip((page - 1) * limit);
        List<jijing> all = mongoTemplate.find(query, jijing.class);
        return r.ok().data(all);
    }

    @Override
    public r getList() {
        List<Gongsi> all = mongoTemplate.findAll(Gongsi.class);
        return r.ok().data(all);
    }
}
