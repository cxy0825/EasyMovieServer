package com.cxy.Dto;

import com.cxy.entry.jijing;
import lombok.Data;

import java.time.LocalDate;

@Data
public class chiyouVo {
    private Long id;
    private Long c_id;
    Long number;
    LocalDate buyDay;
    Double buyPrice;
    jijing jijing;
}
