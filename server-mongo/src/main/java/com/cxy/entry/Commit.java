package com.cxy.entry;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data

public class Commit {
    /*
     * 评论ID
     * */
    @Id
    @Field(value = "_id")
    String id;

    /*
     * 用户ID
     * */
    String userID;

    /*
     * 用户名字
     * */
    String name;

    /*
     * 头像
     * */
    String avatarUrl;

    /*
     * 评论内容
     * */
    String content;
    /*
     * 发布时间
     * */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8", shape = JsonFormat.Shape.STRING)
    LocalDateTime time;


}
