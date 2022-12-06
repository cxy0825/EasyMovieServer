package com.cxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cxy.entry.Moviehouse;
import com.cxy.result.Result;

import java.util.HashMap;

/**
 * @author Cccxy
 * @description 针对表【moviehouse】的数据库操作Service
 * @createDate 2022-11-29 15:48:00
 */
public interface MoviehouseService extends IService<Moviehouse> {

    Result getMovieHouseWithCinema(Integer page, Integer limit, String name);

    Result add(Moviehouse moviehouse);

    Result delete(Long id);


    Result getMovieHouseInfo(Long movieHouseID);

    Result insertSeatByID(Long movieHouseID, HashMap<String, int[]> map);

    Result getMovieHouseWithCinemaById(Long id);

    Result updateSeatById(Long movieHouseID, HashMap<String, int[]> map);
}
