package com.cxy.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxy.entry.Voucher;
import com.cxy.result.Result;
import com.cxy.service.VoucherService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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

    /**
     * 修改优惠券的状态(是否上架优惠券)
     *
     * @param voucher 凭证
     * @return {@link Result}
     */
    @PostMapping("/updateState")
    public Result updateState(@RequestBody Voucher voucher) {
        boolean update = voucherService.updateById(voucher);
        if (update) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }


    @DeleteMapping("/deleteVoucher/{ID}")
    public Result deleteVoucher(@PathVariable Long ID) {
        return voucherService.del(ID);
    }
}
