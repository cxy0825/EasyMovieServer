package com.cxy.clients.mongo;

import com.cxy.entry.Film;
import com.cxy.entry.mongoEntry.MongoMoviehouse;
import com.cxy.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "server-mongo")
public interface MongoClient {
    //传递复杂参数的时候一定要用post,然后去requestBody里面获取
    //用requestParams会转换异常,因为这个是获取凭借在url?后面的,get请求有长度限制所以会被截断
    @PostMapping("/mongo/movieHouse/insert")
    Result insertMovieHouse(@RequestBody MongoMoviehouse mongoMoviehouse);

    @GetMapping("/mongo/movieHouse/info/{movieHouseID}")
    Result getMovieHouseById(@PathVariable("movieHouseID") Long movieHouseID);

    @PostMapping("/mongo/movieHouse/insert/{movieHouseID}")
    Result insertSeatByID(@PathVariable("movieHouseID") Long movieHouseID, @RequestBody int[] arr);

    @PostMapping("/mongo/movieHouse/update/{movieHouseID}")
    Result updateSeatById(@PathVariable("movieHouseID") Long movieHouseID, @RequestBody int[] arr);

    @GetMapping("mongo/public/film/info/{ID}")
    Result getFilmInfoByID(@PathVariable("ID") Long ID);

    @PostMapping("mongo/public/film/info/insert")
    Result insertFilmInfo(@RequestBody Film film);

    @PostMapping("mongo/public/film/info/delete/{ID}")
    boolean deleteFilmInfoById(@PathVariable("ID") Long ID);
}
