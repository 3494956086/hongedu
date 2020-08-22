package com.hong.orderservice.service;

import com.hong.orderservice.entity.TPayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author hong
 * @since 2020-08-14
 */
public interface TPayLogService extends IService<TPayLog> {
   Map createNative(String orderNo);
   void updateOrderStatus(Map<String, String> map);
   Map queryPayStatus(String orderNo);
}
