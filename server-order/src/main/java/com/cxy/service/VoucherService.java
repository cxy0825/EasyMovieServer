package com.cxy.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cxy.entry.Voucher;
import com.cxy.result.Result;

/**
 * @author Cccxy
 * @description 针对表【voucher(全局优惠券所有店铺都能使用)】的数据库操作Service
 * @createDate 2022-12-16 21:37:42
 */
public interface VoucherService extends IService<Voucher> {

    Page<Voucher> getVoucherList(Page<Voucher> voucherPage, Voucher voucher);

    Result updateVoucher(Voucher voucher);

    Result del(Long id);

    Result buyVoucher(Voucher voucher);
}
