package com.cxy.controller;

import com.cxy.entry.Moviehouse;
import com.cxy.result.Result;
import com.cxy.service.MoviehouseService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;

@RestController
@RequestMapping("/movie")
public class MovieHouseController {
    @Resource
    MoviehouseService moviehouseService;


    /**
     * 获取所有电影放映厅列表,并且查出属于哪个影院的
     *
     * @return {@link Result}
     */
    @GetMapping("/movieHouse/list/{page}/{limit}")
    public Result getMovieHouseList(
            @PathVariable("page") Integer page,
            @PathVariable("limit") Integer limit,
            @RequestParam(value = "name", required = false) String name
    ) {
        return moviehouseService.getMovieHouseWithCinema(page, limit, name);
    }

    //查看电影厅的座位信息
    @GetMapping("public/movieHouse/info/{movieHouseID}")
    public Result getMovieHouseInfo(@PathVariable("movieHouseID") Long movieHouseID) {
        return moviehouseService.getMovieHouseInfo(movieHouseID);
    }

    /**
     * 添加或者修改 影厅信息
     *
     * @param moviehouse moviehouse
     * @return {@link Result}
     */
    @PostMapping("/movieHouse/addHouse")
    public Result addHouse(@RequestBody Moviehouse moviehouse) {

        return moviehouseService.add(moviehouse);
    }

    /*
     * 删除电影 放映厅传入ID
     *
     * */
    @DeleteMapping("/movieHouse/deleteHouse/{ID}")
    public Result deleteHouse(@PathVariable("ID") Long ID) {
        return moviehouseService.delete(ID);
    }

    /**
     * 通过电影院ID更新座位信息
     *
     * @param movieHouseID 电影院id
     * @param map          请求体 稀疏矩阵压缩后的数组{ "arr":[0,0,1] }
     * @return {@link Result}
     */
    @PostMapping("/movieHouse/update/{movieHouseID}")
    public Result updateSeatById(
            @PathVariable("movieHouseID") Long movieHouseID,
            @RequestBody HashMap<String, int[]> map
    ) {
        
        return moviehouseService.updateSeatById(movieHouseID, map);
    }
}
