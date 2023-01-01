package com.cxy.entry.mongoEntry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("em_cinema")
public class MongoCinema {
    @Id
    private Long id;
    /**
     * 电影院名字
     */
    private String cinemaName;

    /*
     *
     *经纬度
     * */
    private double[] jw;

    private Double distance;
}
