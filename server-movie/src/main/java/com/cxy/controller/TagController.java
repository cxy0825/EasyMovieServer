package com.cxy.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxy.entry.Tag;
import com.cxy.result.Result;
import com.cxy.service.TagIndexService;
import com.cxy.service.TagService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/movie")
public class TagController {
    @Resource
    TagService tagService;
    @Resource
    TagIndexService tagIndexService;

    //根据标签名字获取详情
    @GetMapping("/public/tag/Info")
    public Result getTagInfoByName(@RequestParam("name") String name) {
        LambdaQueryWrapper<Tag> tagLambdaQueryWrapper = new LambdaQueryWrapper<>();
        tagLambdaQueryWrapper.like(Tag::getTagName, name);
        List<Tag> list = tagService.list(tagLambdaQueryWrapper);

        return Result.ok().data(list);
    }

    //查询标签表中的标签信息
    @GetMapping("/public/tag/Info/{page}/{limit}")
    public Result getTagInfo(@PathVariable("page") Integer page, @PathVariable("limit") Integer limit) {
        Page<Tag> tagPage = new Page<>(page, limit);
        tagService.page(tagPage);

        return Result.ok().data(tagPage);
    }

    //根据电影ID获取标签信息
    @GetMapping("/public/tag/Info/{ID}")
    public Result getTagInfoById(@PathVariable("ID") Long ID) {
        LambdaQueryWrapper<Tag> tagLambdaQueryWrapper = new LambdaQueryWrapper<>();
        tagLambdaQueryWrapper.eq(Tag::getId, ID);
        Tag one = tagService.getOne(tagLambdaQueryWrapper);

        return Result.ok().data(one);
    }

    //添加或者更新标签
    @PostMapping("/tag/save")
    public Result saveOrUpdateTag(@RequestBody Tag tag) {
        //不允许名字相同的出现
        LambdaUpdateWrapper<Tag> tagLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        tagLambdaUpdateWrapper.eq(Tag::getTagName, tag.getTagName());
        int count = tagService.count(tagLambdaUpdateWrapper);
        if (count > 0) {
            return Result.fail().message("已经存在相同名称的标签");
        }
        boolean update = tagService.saveOrUpdate(tag);
        return Result.ok();

    }

    //删除标签 根据ID
    @DeleteMapping("/tag/del/{ID}")
    public Result deleteTagById(@PathVariable Long ID) {
        boolean b = tagService.removeById(ID);
        if (b) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    //删除标签 根据名字
    @DeleteMapping("/tag/del")
    public Result deleteTagByName(@RequestParam String tagName) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Tag::getTagName, tagName);
        boolean b = tagService.remove(queryWrapper);
        if (b) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    //根据电影ID移除指定的标签
    @DeleteMapping("/tag/del/film/{filmID}/{tagID}")
    public Result deleteTagWithFilmID(
            @PathVariable Long filmID,
            @PathVariable Long tagID
    ) {
        return tagIndexService.deleteTagWithFilmID(filmID, tagID);
    }

    //根据电影ID添加标签
    @PostMapping("/tag/add/film/{filmID}/{tagID}")
    public Result addTagWithFilmID(
            @PathVariable Long filmID,
            @PathVariable Long tagID) {
        return tagIndexService.addTagWithFilmID(filmID, tagID);
    }
}
