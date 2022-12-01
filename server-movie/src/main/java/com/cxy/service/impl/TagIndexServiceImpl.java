package com.cxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxy.entry.TagIndex;
import com.cxy.mapper.TagIndexMapper;
import com.cxy.service.TagIndexService;
import org.springframework.stereotype.Service;

/**
 * @author Cccxy
 * @description 针对表【tag_index(电影和标签关系表)】的数据库操作Service实现
 * @createDate 2022-11-29 15:48:00
 */
@Service
public class TagIndexServiceImpl extends ServiceImpl<TagIndexMapper, TagIndex>
        implements TagIndexService {

}




