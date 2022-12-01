package com.cxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxy.entry.Performer;
import com.cxy.mapper.PerformerMapper;
import com.cxy.service.PerformerService;
import org.springframework.stereotype.Service;

/**
 * @author Cccxy
 * @description 针对表【performer(演员信息表)】的数据库操作Service实现
 * @createDate 2022-11-29 15:48:00
 */
@Service
public class PerformerServiceImpl extends ServiceImpl<PerformerMapper, Performer>
        implements PerformerService {

}




