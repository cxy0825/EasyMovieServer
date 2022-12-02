package com.cxy.entry.mongoEntry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("em_moviehouse")
public class MongoMoviehouse implements Serializable {
    @Id
    private Long id;
    /**
     * 电影院ID
     */
    private Long cinemaId;

    /**
     * 放映厅名字
     */
    private String movieHouseName;

    /**
     *
     */
    //已经使用过的座位信息
    //稀疏矩阵的压缩 数组第一项永远是 有多少行 多少列 有几个值
    //0表示没有人购买 1表示有人购买 2表示维修中
    private int[][] seatInfo;


}