package com.cxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cxy.entry.TagIndex;
import com.cxy.result.Result;

/**
 * @author Cccxy
 * @description 针对表【tag_index(电影和标签关系表)】的数据库操作Service
 * @createDate 2022-11-29 15:48:00
 */
public interface TagIndexService extends IService<TagIndex> {

    Result deleteTagWithFilmID(Long filmID, Long tagID);

    Result addTagWithFilmID(Long filmID, Long tagID);
}
