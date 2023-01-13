package com.cxy.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxy.entry.Voucher;
import com.cxy.result.Result;
import com.cxy.service.VoucherService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/order/voucher")
public class VoucherController {
    @Resource
    VoucherService voucherService;

    /*
     * 查询优惠券,分页 根据权限返回列表
     *
     * */
    @GetMapping("/voucherList/{page}/{limit}")
    public Result voucherList(
            @PathVariable("page") Integer page,
            @PathVariable("limit") Integer limit,
            Voucher voucher
    ) {
        Page<Voucher> voucherPage = new Page<>(page, limit);
        voucherService.getVoucherList(voucherPage, voucher);
        return Result.ok().data(voucherPage);
    }

    /*
     * 根据电影院ID查询优惠券
     * */
    @GetMapping("/voucherList/{cinemaID}")
    public Result voucherList(@PathVariable("cinemaID") Long cinemaID) {

        return voucherService.getVoucherList(cinemaID);
    }

    /*
     * 查询用户持有的优惠券
     * */
    @GetMapping("/user")
    public Result voucherByUser(@RequestParam(value = "cinemaID", required = false) Long cinemaID) {
        List<Voucher> list = voucherService.getVoucherListByUserID(cinemaID);
        return Result.ok().data(list);
    }

    /**
     * 添加或者修改优惠券
     *
     * @param voucher 凭证
     * @return {@link Result}
     */
    @PostMapping("/updateVoucher")
    public Result updateVoucher(@RequestBody Voucher voucher) {
        return voucherService.updateVoucher(voucher);

    }

    /*
     * 删除优惠券
     *
     * */
    @DeleteMapping("/deleteVoucher/{ID}")
    public Result deleteVoucher(@PathVariable Long ID) {
        return voucherService.del(ID);
    }


}
