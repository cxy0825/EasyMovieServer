package com.cxy.vo.resultVo;

import com.cxy.entry.Cinema;
import com.cxy.entry.Moviehouse;
import lombok.Data;

import java.io.Serializable;

@Data
public class MovieHouseVo extends Moviehouse implements Serializable {
    private Cinema cinema;
}
