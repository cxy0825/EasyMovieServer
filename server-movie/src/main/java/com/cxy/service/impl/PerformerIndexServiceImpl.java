package com.cxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxy.entry.PerformerIndex;
import com.cxy.service.PerformerIndexService;
import com.cxy.mapper.PerformerIndexMapper;
import org.springframework.stereotype.Service;

/**
* @author Cccxy
* @description 针对表【performer_index(演员和影片之间的关系表)】的数据库操作Service实现
* @createDate 2022-12-27 20:44:41
*/
@Service
public class PerformerIndexServiceImpl extends ServiceImpl<PerformerIndexMapper, PerformerIndex>
    implements PerformerIndexService{

}




