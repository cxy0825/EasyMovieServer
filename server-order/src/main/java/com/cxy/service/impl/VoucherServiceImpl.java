package com.cxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxy.Utils.ThreadLocalUtil;
import com.cxy.clients.mongo.RedisClient;
import com.cxy.entry.HoldVoucher;
import com.cxy.entry.Token;
import com.cxy.entry.Voucher;
import com.cxy.mapper.VoucherMapper;
import com.cxy.result.Result;
import com.cxy.result.ResultEnum;
import com.cxy.result.redisKey;
import com.cxy.service.HoldVoucherService;
import com.cxy.service.PaymentService;
import com.cxy.service.VoucherService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.beans.Transient;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Cccxy
 * @description 针对表【voucher(全局优惠券所有店铺都能使用)】的数据库操作Service实现
 * @createDate 2022-12-16 21:37:42
 */
@Service
public class VoucherServiceImpl extends ServiceImpl<VoucherMapper, Voucher>
        implements VoucherService {
    @Resource
    RedisClient redisClient;
    @Resource
    RabbitTemplate rabbitTemplate;
    @Resource
    PaymentService paymentService;
    @Resource
    HoldVoucherService holdVoucherService;

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
    public Result getVoucherList(Long cinemaID) {
        LambdaUpdateWrapper<Voucher> voucherLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        voucherLambdaUpdateWrapper.eq(Voucher::getState, 1);
        voucherLambdaUpdateWrapper.eq(Voucher::getCinemaId, cinemaID);
        List<Voucher> vouchers = baseMapper.selectList(voucherLambdaUpdateWrapper);
        return Result.ok().data(vouchers);
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
            //判断是否修改了优惠券的库存,如果有修改就去redis更新优惠券的库存
            update = baseMapper.updateById(voucher) > 0;

        } else {
            voucher.setCinemaId(token.getCinemaId());
            update = baseMapper.insert(voucher) > 0;
        }
        //如果涉及到库存更新就同步redis
        //如果是添加的话库存数量肯定不为0
        if (voucher.getStock() != null) {
            //更新redis中库存数量
            String key = redisKey.VOUCHER_STOCK.getKey() + voucher.getId().toString();
            redisClient.setStock(key, Long.valueOf(voucher.getStock()));
        }

        return Result.ok();


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
        //删除redis中的缓存库存
        if (del) {
            redisClient.delStock(redisKey.VOUCHER_STOCK.getKey() + id);
            redisClient.delStock(redisKey.VOUCHER_ALREADY_BOUGHT.getKey() + id);
            return Result.ok();
        } else {
            return Result.fail();
        }

    }

    @Override
    public List<Voucher> getVoucherListByUserID(Long cinemaID) {

        Token token = (Token) ThreadLocalUtil.get();
        Long id = token.getId();
        LambdaQueryWrapper<HoldVoucher> voucherLambdaQueryWrapper = new LambdaQueryWrapper<>();
        voucherLambdaQueryWrapper.eq(HoldVoucher::getUserId, id);
        voucherLambdaQueryWrapper.eq(HoldVoucher::getState, 1);
        List<HoldVoucher> list = holdVoucherService.list(voucherLambdaQueryWrapper);
        List<Voucher> voucherList = list.stream().map(item -> {
            //如果请求参数中包含电影院ID就查找指定电影院
            if (null != cinemaID) {
                return baseMapper.selectOne(new LambdaQueryWrapper<Voucher>().eq(Voucher::getCinemaId, cinemaID).eq(Voucher::getId, item.getVoucherId()));
            }
            return baseMapper.selectById(item.getVoucherId());
        }).collect(Collectors.toList());
        return voucherList;
    }


}




