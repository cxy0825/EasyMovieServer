package com.cxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cxy.entry.TagIndex;
import org.apache.ibatis.annotations.Param;

import java.util.HashSet;

/**
 * @author Cccxy
 * @description 针对表【tag_index(电影和标签关系表)】的数据库操作Mapper
 * @createDate 2022-11-29 15:48:00
 * @Entity com.cxy.entry.TagIndex
 */
public interface TagIndexMapper extends BaseMapper<TagIndex> {
    //根据filmID查询对应的标签
    HashSet<String> selectTagsByID(@Param("ID") Long ID);
}




