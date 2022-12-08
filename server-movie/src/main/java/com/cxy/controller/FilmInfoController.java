package com.cxy.controller;

import com.cxy.result.Result;
import com.cxy.service.FilmInfoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

//电影海报,宣传视频,标签修改
@RestController
@RequestMapping("/movie")
public class FilmInfoController {

    @Resource
    FilmInfoService filmInfoService;

    /*
     *
     * 更新电影的海报
     * */
    @PostMapping("/filmInfo/poster/update/{ID}")
    public Result updatePoster(@RequestPart("uploadFile") MultipartFile multipartFile, @PathVariable Long ID) {


        return filmInfoService.updatePoster(multipartFile, ID);
    }

    /*
     * 更新电影的视频
     * */
    @PostMapping("/filmInfo/video/update/{ID}")
    public Result updateVideo(@RequestPart("uploadFile") MultipartFile multipartFile, @PathVariable Long ID) {

        return filmInfoService.updateVideo(multipartFile, ID);
    }


}
