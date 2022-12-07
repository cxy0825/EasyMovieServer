package com.cxy.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxy.entry.Tag;
import com.cxy.result.Result;
import com.cxy.service.TagService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/movie")
public class TagController {
    @Resource
    TagService tagService;

    //根据标签名字获取详情
    @GetMapping("/public/tag/Info")
    public Result getTagInfoByName(@RequestParam("name") String name) {
        LambdaQueryWrapper<Tag> tagLambdaQueryWrapper = new LambdaQueryWrapper<>();
        tagLambdaQueryWrapper.eq(Tag::getTagName, name);
        Tag one = tagService.getOne(tagLambdaQueryWrapper);

        return Result.ok().data(one);
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

        boolean update = tagService.saveOrUpdate(tag);
        if (update) {
            return Result.ok();
        } else {
            return Result.fail();
        }

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

    //删除标签 根据ID
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
}
