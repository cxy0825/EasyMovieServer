package com.cxy.service.impl;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.cxy.clients.mongo.RedisClient;
import com.cxy.entry.Commit;
import com.cxy.entry.Commit_info;
import com.cxy.entry.Token;
import com.cxy.entry.commitRequest;
import com.cxy.service.CommitService;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service
public class CommitServiceImpl implements CommitService {

    @Resource
    RedisClient redisClient;
    @Resource
    MongoTemplate mongoTemplate;

    @Override
    public Boolean add(String token, commitRequest commitRequest) {
        String filmID = commitRequest.getFilmID();
        //从token中分离签名出来
        String sign = token.substring(token.lastIndexOf(".") + 1);
        try {
            JWT jwt = JWTUtil.parseToken(token);
            String account = (String) jwt.getPayload("account");
            Token userInfo = redisClient.getUserInfo(account, sign);
            Commit commit = new Commit();
            commit.setContent(commitRequest.getContent());
            commit.setName(userInfo.getName());
            commit.setTime(LocalDateTime.now());
            commit.setUserID(userInfo.getId().toString());
            commit.setAvatarUrl(userInfo.getAvatarUrl());
            commit.setId(new ObjectId().toString());
            //先查看有没有这条记录
            long count = mongoTemplate.count(Query.query(Criteria.where("_id").is(filmID)), "em_commit");
            if (count <= 0) {
                Commit_info commit_info = new Commit_info();
                commit_info.set_id(filmID);
                mongoTemplate.insert(commit_info, "em_commit");
            }
//            添加数据
            Update update = new Update();
            update.push("commits", commit);
            UpdateResult updateResult = mongoTemplate.updateFirst(
                    Query.query(Criteria.where("_id").is(filmID)),
                    update,
                    "em_commit"
            );
//            插入0条就返回失败
            if (updateResult.getModifiedCount() <= 0) {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Commit_info list(Long filmID) {
        Commit_info commit_info = mongoTemplate.findById(filmID.toString(), Commit_info.class);
        return commit_info;
    }
}
