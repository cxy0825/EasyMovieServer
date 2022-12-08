package com.cxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cxy.entry.FilmInfo;
import com.cxy.result.Result;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Cccxy
 * @description 针对表【film_info】的数据库操作Service
 * @createDate 2022-11-29 15:48:00
 */
public interface FilmInfoService extends IService<FilmInfo> {


    Result updatePoster(MultipartFile multipartFile, Long id);

    Result updateVideo(MultipartFile multipartFile, Long id);
}
