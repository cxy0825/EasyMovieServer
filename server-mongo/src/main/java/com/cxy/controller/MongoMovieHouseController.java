package com.cxy.controller;

import com.cxy.entry.mongoEntry.MongoMoviehouse;
import com.cxy.result.Result;
import com.cxy.service.MongoMoviehouseServer;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/mongo")
public class MongoMovieHouseController {
    @Resource
    MongoMoviehouseServer mongoMoviehouseServer;

    /**
     * 通过id获取电影院放映厅
     *
     * @param movieHouseID 电影院放映厅ID
     * @return {@link Result}
     *///查询某个电影播放厅座位信息
    @GetMapping("/movieHouse/info/{movieHouseID}")
    public Result getMovieHouseById(@PathVariable("movieHouseID") Long movieHouseID) {

        return mongoMoviehouseServer.getMovieHouseById(movieHouseID);
    }

    /**
     * 插入电影院放映厅信息
     *
     * @return {@link Result}
     */
    @PostMapping("/movieHouse/insert")
    public Result insertMovieHouse(@RequestBody MongoMoviehouse mongoMoviehouse) {

        return mongoMoviehouseServer.insertData(mongoMoviehouse);
    }

    /**
     * 通过id更新放映厅的座位信息
     *
     * @param movieHouseID 电影院id
     * @param arr          稀疏数组
     * @return {@link Result}
     */
    @PostMapping("/movieHouse/update/{movieHouseID}")
    public Result updateSeatById(@PathVariable("movieHouseID") Long movieHouseID, @RequestBody int[] arr) {

        return mongoMoviehouseServer.updateSeatById(movieHouseID, arr);
    }
}
