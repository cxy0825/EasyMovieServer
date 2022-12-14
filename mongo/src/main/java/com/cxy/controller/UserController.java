package com.cxy.controller;

import com.cxy.common.r;
import com.cxy.entry.User;
import com.cxy.service.UserServer;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/mongo/user")
@CrossOrigin
public class UserController {
    @Resource
    UserServer userServer;

    @PostMapping("/login")
    public r login(@RequestBody User user) {
        return userServer.login(user);
    }

    //查看用户持有哪些基金
    @GetMapping("/chiyou")
    public r chiyou(@RequestParam(required = false) String value) {
        return userServer.getChiyou();
    }

    //购买基金
    @GetMapping("/buy")
    public r buy(@RequestParam("ID") Long ID,
                 @RequestParam("number") Long number,
                 @RequestParam("price") double price) {
        return userServer.buy(ID, number, price);
    }

    //    卖出基金
    @GetMapping("/sell")
    public r sell(@RequestParam("ID") Long ID) {
        return userServer.sell(ID);
    }

    //查看用户的收藏
    @GetMapping("/shouchang")
    public r shouchang() {
        return userServer.getShouchang();
    }

    //添加收藏
    @GetMapping("/shouchang/add/{ID}")
    public r add(@PathVariable Long ID) {
        return userServer.add(ID);
    }

    //取消收藏
    @GetMapping("/shouchang/q/{ID}")
    public r q(@PathVariable Long ID) {
        return userServer.q(ID);
    }
}
