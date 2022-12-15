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
     * 通过id获取电影院放映厅下位置信息
     *
     * @param movieHouseID 电影院放映厅ID
     * @return {@link Result}
     *///查询某个电影播放厅座位信息
    @GetMapping("/movieHouse/info/{movieHouseID}")
    public Result getSeatInfoById(@PathVariable("movieHouseID") Long movieHouseID) {

        return mongoMoviehouseServer.getMovieHouseById(movieHouseID);
    }

    /**
     * 插入电影院放映厅信息到mongo
     *
     * @return {@link Result}
     */
    @PostMapping("/movieHouse/insert")
    public Result insertMovieHouse(@RequestBody MongoMoviehouse mongoMoviehouse) {

        return mongoMoviehouseServer.insertData(mongoMoviehouse);
    }

    /**
     * 在对应ID的放映厅下插入位置信息
     *
     * @param movieHouseID 电影院id
     * @param arr          稀疏数组
     * @return {@link Result}
     */
    @PostMapping("/movieHouse/insert/{movieHouseID}")
    public Result insertSeatByID(@PathVariable("movieHouseID") Long movieHouseID, @RequestBody int[] arr) {

        return mongoMoviehouseServer.insertSeatByID(movieHouseID, arr);
    }

    /*
     * 更新电影放映厅中的座位信息
     * */
    @PostMapping("/movieHouse/update/{movieHouseID}")
    public Result updateSeatByID(@PathVariable("movieHouseID") Long movieHouseID, @RequestBody Integer[] arr) {

        return mongoMoviehouseServer.updateSeatByID(movieHouseID, arr);
    }

    /*
     *
     * 在mongo中删除电影放映厅的信息
     * */
    @PostMapping("/movieHouse/del/{movieHouseID}")
    public Result delMovieHouseById(@PathVariable Long movieHouseID) {
        return mongoMoviehouseServer.delMovieHouseById(movieHouseID);
    }

}
