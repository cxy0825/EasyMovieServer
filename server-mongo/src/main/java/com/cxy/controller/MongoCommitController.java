package com.cxy.controller;

import com.cxy.entry.Commit_info;
import com.cxy.entry.commitRequest;
import com.cxy.result.Result;
import com.cxy.service.CommitService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/mongo/commit")
public class MongoCommitController {
    @Resource
    CommitService commitService;

    /*
     * 添加评论
     * */
    @PostMapping("add")
    public Result add(
            @RequestHeader("token") String token,
            @RequestBody commitRequest commitRequest
    ) {
        Boolean flag = commitService.add(token, commitRequest);
        return Result.ok();
    }

    /*
     * 根据电影ID查询评论
     * */
    @GetMapping("list")
    public Result list(
            @RequestParam("filmID") Long filmID) {
        Commit_info list = commitService.list(filmID);
        return Result.ok().data(list);
    }
}
