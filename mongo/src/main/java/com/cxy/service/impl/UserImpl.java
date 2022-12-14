package com.cxy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.cxy.Dto.chiyouVo;
import com.cxy.common.r;
import com.cxy.entry.Chiyou;
import com.cxy.entry.User;
import com.cxy.entry.jijing;
import com.cxy.service.UserServer;
import com.mongodb.client.result.UpdateResult;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserImpl implements UserServer {
    @Resource
    MongoTemplate mongoTemplate;

    @Override
    public r login(User user) {

        Criteria criteria = new Criteria();
        criteria.andOperator(
                Criteria.where("account").is(user.getAccount()),
                Criteria.where("password").is(user.getPassword())
        );
        Query query = new Query(criteria);
        User u = mongoTemplate.findOne(query, User.class);
        return r.ok().data(u);
    }

    @Override
    public r getChiyou() {
        Query query = new Query(Criteria.where("_id").is(1));

        User userInfo = mongoTemplate.findOne(query, User.class);
        List<Chiyou> chiyou = userInfo.getChiyou();
        List<chiyouVo> jijings = chiyou.stream().map(item -> {
            chiyouVo chiyouVo = new chiyouVo();
            BeanUtil.copyProperties(item, chiyouVo);
            Query chiyouQuery = new Query(Criteria.where("_id").is(item.getId()));
            jijing jijing = mongoTemplate.findOne(chiyouQuery, jijing.class);
            chiyouVo.setJijing(jijing);
            return chiyouVo;
        }).collect(Collectors.toList());

        return r.ok().data(jijings);
    }

    @Override
    public r getShouchang() {
        Query query = new Query(Criteria.where("_id").is(1));
        User userInfo = mongoTemplate.findOne(query, User.class);
        List<Long> shouchang = userInfo.getShouchang();
        Query chiyouQuery = new Query(Criteria.where("_id").in(shouchang));
        List<jijing> jijings = mongoTemplate.find(chiyouQuery, jijing.class);
        return r.ok().data(jijings);
    }

    @Override
    public r buy(Long ID, Long number, double price) {
        Chiyou chiyou = new Chiyou();
        chiyou.setC_id(System.currentTimeMillis());
        chiyou.setBuyDay(LocalDate.now());
        chiyou.setId(ID);
        chiyou.setNumber(number);
        chiyou.setBuyPrice(price);
        Query query = new Query(
                Criteria.where("_id").is(1)
        );
        Update update = new Update();
        update.push("chiyou", chiyou);

//        update.pull("chiyoiu.$.id", ID);
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, User.class);
//        System.out.println(updateResult.getModifiedCount());
        return r.ok().data(updateResult);
    }

    @Override
    public r sell(Long id) {
        Criteria criteria = new Criteria();
        criteria.andOperator(
                Criteria.where("_id").is(1)
        );
        Query query = new Query(criteria);
        Update update = new Update();
        Chiyou chiyou = new Chiyou();
        chiyou.setC_id(id);

        update.pull("chiyou", chiyou);
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, User.class);
        if (updateResult.getModifiedCount() > 0) {
            return r.ok().message("卖出成功");
        } else {
            return r.file().message("卖出失败,原因:你的没有这只基金");
        }

    }

    @Override
    public r add(Long id) {
        Query query = Query.query(Criteria.where("_id").is(1));
        Update update = new Update();
        update.addToSet("shouchang", id);
        UpdateResult upsert = mongoTemplate.upsert(query, update, User.class);
        if (upsert.getModifiedCount() > 0) {
            return r.ok().message("收藏");
        } else {
            return r.file().message("你已经收藏过了");
        }
    }

    @Override
    public r q(Long id) {
        Criteria criteria = new Criteria();
        criteria.andOperator(
                Criteria.where("_id").is(1)
        );
        Query query = new Query(criteria);
        Update update = new Update();
        update.pull("shouchang", id);
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, User.class);
        if (updateResult.getModifiedCount() > 0) {
            return r.ok().message("取消收藏成功");
        } else {
            return r.file().message("取消收藏失败");
        }
    }
}
