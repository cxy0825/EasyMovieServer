package com.cxy.entry.mongoEntry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("em_FilmInfo")
public class MongoFilmInfo implements Serializable {
    /**
     *
     */
    @Id
    private Long id;

    /**
     * 影片名字
     */
    private String filmName;

    /**
     * 影片简介
     */
    private String about;

    /**
     * 电影上映时间
     */
    private LocalDateTime releaseTime;

    /**
     * 电影时长
     */
    private Double duration;

    /**
     * 电影评分
     */
    private Double score;
    //电影宣传视频地址

    private List<String> videoUrls = new ArrayList<>();
    //电影宣传海报地址

    private List<String> posterUrls = new ArrayList<>();

    //电影标签
    private List<String> tags = new ArrayList<>();

}