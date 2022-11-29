package com.cxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxy.entry.MovieSet;
import com.cxy.service.MovieSetService;
import com.cxy.mapper.MovieSetMapper;
import org.springframework.stereotype.Service;

/**
* @author Cccxy
* @description 针对表【movie_set(排片信息)】的数据库操作Service实现
* @createDate 2022-11-29 15:48:00
*/
@Service
public class MovieSetServiceImpl extends ServiceImpl<MovieSetMapper, MovieSet>
    implements MovieSetService{

}




