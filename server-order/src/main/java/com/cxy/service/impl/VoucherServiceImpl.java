package com.cxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxy.Utils.ThreadLocalUtil;
import com.cxy.entry.Token;
import com.cxy.entry.Voucher;
import com.cxy.mapper.VoucherMapper;
import com.cxy.result.Result;
import com.cxy.result.ResultEnum;
import com.cxy.service.VoucherService;
import org.springframework.stereotype.Service;

import java.beans.Transient;

/**
 * @author Cccxy
 * @description 针对表【voucher(全局优惠券所有店铺都能使用)】的数据库操作Service实现
 * @createDate 2022-12-16 21:37:42
 */
@Service
public class VoucherServiceImpl extends ServiceImpl<VoucherMapper, Voucher>
        implements VoucherService {


    @Override
    public Page<Voucher> getVoucherList(Page<Voucher> voucherPage, Voucher voucher) {
        Token token = (Token) ThreadLocalUtil.get();
        //从token中获取ID
        String type = token.getType();
        LambdaQueryWrapper<Voucher> voucherLambdaQueryWrapper = new LambdaQueryWrapper<>();

        //root管理员查询全部
        if (!type.equals("root")) {
            voucherLambdaQueryWrapper.eq(Voucher::getCinemaId, token.getCinemaId());
        }
        //传入电影院的名字进行查询
        voucherLambdaQueryWrapper.like(voucher.getCinemaName() != null, Voucher::getCinemaName, voucher.getCinemaName());
        //传入电影院的ID进行查询
        voucherLambdaQueryWrapper.eq(voucher.getCinemaId() != null, Voucher::getCinemaId, voucher.getCinemaId());
        //根据传入的优惠券ID进行查找
        voucherLambdaQueryWrapper.eq(voucher.getId() != null, Voucher::getId, voucher.getId());
        baseMapper.selectPage(voucherPage, voucherLambdaQueryWrapper);
        return voucherPage;
    }

    @Override
    @Transient
    public Result updateVoucher(Voucher voucher) {

        Token token = (Token) ThreadLocalUtil.get();
        boolean after = voucher.getStartTime().isAfter(voucher.getEndTime());
        //判断优惠券开始时间是否小于结束时间
        if (after) {
            return Result.fail(ResultEnum.ERROR_PARAMS);
        }
        //如果没有ID说明是添加,否则是修改
        boolean update = false;
        if (voucher.getId() != null) {
            update = baseMapper.updateById(voucher) > 0;
        } else {
            voucher.setCinemaId(token.getCinemaId());
            update = baseMapper.insert(voucher) > 0;
        }
        if (update) {
            return Result.ok();
        } else {
            return Result.fail();
        }

    }

    @Override
    public Result del(Long id) {
        Token token = (Token) ThreadLocalUtil.get();
        LambdaQueryWrapper<Voucher> voucherLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //所属电影院ID
        voucherLambdaQueryWrapper.eq(Voucher::getCinemaId, token.getCinemaId());
        //删除优惠券的ID
        voucherLambdaQueryWrapper.eq(Voucher::getId, id);
        boolean del = baseMapper.delete(voucherLambdaQueryWrapper) > 0;
        if (del) {
            return Result.ok();
        } else {
            return Result.fail();
        }

    }
}




