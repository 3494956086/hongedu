package com.hong.orderservice.service;

import com.hong.orderservice.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author hong
 * @since 2020-08-14
 */
public interface TOrderService extends IService<TOrder> {
    String saveOrder(String courseId,String memberId);
}
