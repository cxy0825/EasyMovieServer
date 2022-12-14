package com.cxy.entry;


import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@Data
public class Chiyou {
    @Field(value = "id")
    private Long id;
    @Field(value = "c_id")
    private Long c_id;
    Long number;
    LocalDate buyDay;
    Double buyPrice;
}
