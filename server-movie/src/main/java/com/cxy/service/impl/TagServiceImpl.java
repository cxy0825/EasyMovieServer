package com.cxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxy.entry.Tag;
import com.cxy.mapper.TagMapper;
import com.cxy.service.TagService;
import org.springframework.stereotype.Service;

/**
 * @author Cccxy
 * @description 针对表【tag(标签表)】的数据库操作Service实现
 * @createDate 2022-11-29 15:48:00
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
        implements TagService {

}




