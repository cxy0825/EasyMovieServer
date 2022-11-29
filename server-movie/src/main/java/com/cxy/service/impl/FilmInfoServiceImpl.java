package com.cxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxy.entry.FilmInfo;
import com.cxy.service.FilmInfoService;
import com.cxy.mapper.FilmInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author Cccxy
* @description 针对表【film_info】的数据库操作Service实现
* @createDate 2022-11-29 15:48:00
*/
@Service
public class FilmInfoServiceImpl extends ServiceImpl<FilmInfoMapper, FilmInfo>
    implements FilmInfoService{

}




