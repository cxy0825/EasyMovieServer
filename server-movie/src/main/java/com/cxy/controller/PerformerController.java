package com.cxy.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxy.entry.Performer;
import com.cxy.entry.PerformerIndex;
import com.cxy.result.Result;
import com.cxy.service.FileService;
import com.cxy.service.PerformerIndexService;
import com.cxy.service.PerformerService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/movie/performer")
public class PerformerController {

    @Resource
    PerformerService performerService;

    @Resource
    PerformerIndexService performerIndexService;
    @Resource
    FileService fileService;

    /**
     * 得到信息列表
     *
     * @param page      页面
     * @param limit     限制
     * @param performer 表演者
     * @return {@link Result}
     */
    @GetMapping("public/info/list/{page}/{limit}")
    public Result getInfoList(
            @PathVariable("page") Integer page,
            @PathVariable("limit") Integer limit,
            Performer performer
    ) {
        Page<Performer> performerPage = new Page<>(page, limit);
        Page<Performer> p = performerService.getInfoList(performerPage, performer);
        return Result.ok().data(p);
    }

    //根据电影ID查询演员信息
    @GetMapping("public/info/{filmID}")
    public Result getInfoByFilmID(@PathVariable("filmID") Long ID) {
        return performerService.getInfo(ID);
    }

    //根据演员ID查询演员的详细信息
    @GetMapping("public/performerInfo/{ID}")
    public Result getInfoByID(@PathVariable("ID") Long ID) {
        return performerService.getInfoByID(ID);
    }

    //根据演员ID查询演员演过什么电影
    @GetMapping("public/performInfo/{ID}")
    public Result getPerformByID(@PathVariable("ID") Long ID) {
        return performerService.getPerformByID(ID);
    }

    //给电影添加演员
    @GetMapping("/addPerformer")
    public Result addPerformer(
            @RequestParam("filmID") Long filmID,
            @RequestParam("performerID") Long performerID
    ) {
        PerformerIndex performerIndex = new PerformerIndex();
        performerIndex.setFilmId(filmID);
        performerIndex.setPerformerId(performerID);
        performerIndexService.save(performerIndex);
        return Result.ok();
    }

    @GetMapping("del/{ID}")
    public Result del(@PathVariable("ID") Long ID) {
        performerService.removeById(ID);
        return Result.ok();
    }

    @PostMapping("update")
    public Result update(@RequestBody Performer performer) {
        performerService.saveOrUpdate(performer);
        return Result.ok();
    }

    @PostMapping("upload/{ID}")
    public Result upload(@RequestPart("uploadFile") MultipartFile multipartFile, @PathVariable("ID") Long ID) {
        String upload = fileService.upload(multipartFile);
        LambdaUpdateWrapper<Performer> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Performer::getId, ID);
        updateWrapper.set(Performer::getPerformerPicUrl, upload);
        performerService.update(updateWrapper);
        return Result.ok().data(upload);
    }

    //删除电影下对应的演员
    @GetMapping("del/index")
    public Result delIndex(

            @RequestParam("filmID") Long filmID,
            @RequestParam("performerID") Long performerID

    ) {
        performerIndexService.remove(
                new LambdaQueryWrapper<PerformerIndex>()
                        .eq(PerformerIndex::getFilmId, filmID)
                        .eq(PerformerIndex::getPerformerId, performerID)
        );
        return Result.ok();
    }
}
