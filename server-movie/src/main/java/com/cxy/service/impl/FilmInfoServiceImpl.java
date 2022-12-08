package com.cxy.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxy.clients.mongo.MongoClient;
import com.cxy.entry.FilmInfo;
import com.cxy.mapper.FilmInfoMapper;
import com.cxy.result.Result;
import com.cxy.service.FileService;
import com.cxy.service.FilmInfoService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author Cccxy
 * @description 针对表【film_info】的数据库操作Service实现
 * @createDate 2022-11-29 15:48:00
 */
@Service
public class FilmInfoServiceImpl extends ServiceImpl<FilmInfoMapper, FilmInfo>
        implements FilmInfoService {
    @Resource
    FileService fileService;
    @Resource
    FilmInfoService filmInfoService;
    @Resource
    MongoClient mongoClient;

    @Override
    public Result updatePoster(MultipartFile multipartFile, Long id) {
        String upload = fileService.upload(multipartFile);
        //上传成功会返回地址
        //更新数据库
        if (StrUtil.isNotBlank(upload)) {
            System.out.println(upload);
            LambdaUpdateWrapper<FilmInfo> filmInfoLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            filmInfoLambdaUpdateWrapper.eq(FilmInfo::getFilmId, id);
            filmInfoLambdaUpdateWrapper.setSql("poster_url=" + "'" + upload + "'");
            boolean update = filmInfoService.update(filmInfoLambdaUpdateWrapper);
            if (update) {
                //删除mongo中的缓存
                mongoClient.deleteFilmInfoById(id);
                return Result.ok();
            }
        }

        return Result.fail();
    }

    @Override
    public Result updateVideo(MultipartFile multipartFile, Long id) {
        String upload = fileService.upload(multipartFile);
        //上传成功会返回地址
        //更新数据库
        if (StrUtil.isNotBlank(upload)) {
            System.out.println(upload);
            LambdaUpdateWrapper<FilmInfo> filmInfoLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            filmInfoLambdaUpdateWrapper.eq(FilmInfo::getFilmId, id);
            filmInfoLambdaUpdateWrapper.setSql("video_url=" + "'" + upload + "'");
            boolean update = filmInfoService.update(filmInfoLambdaUpdateWrapper);
            if (update) {
                //删除mongo中的缓存
                mongoClient.deleteFilmInfoById(id);
                return Result.ok();
            }
        }

        return Result.fail();
    }
}




