package com.cxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxy.entry.Film;
import com.cxy.service.FilmService;
import com.cxy.mapper.FilmMapper;
import org.springframework.stereotype.Service;

/**
* @author Cccxy
* @description 针对表【film(电影影片信息)】的数据库操作Service实现
* @createDate 2022-11-29 15:48:00
*/
@Service
public class FilmServiceImpl extends ServiceImpl<FilmMapper, Film>
    implements FilmService{

}




