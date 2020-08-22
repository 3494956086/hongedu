package com.hong.orderservice.service.impl;

import com.hong.commonutils.utils.OrderNoUtil;
import com.hong.commonutils.vo.CourseInfoForm;
import com.hong.commonutils.vo.UcenterMember;
import com.hong.orderservice.client.EduClient;
import com.hong.orderservice.client.UcenterClient;
import com.hong.orderservice.entity.TOrder;
import com.hong.orderservice.mapper.TOrderMapper;
import com.hong.orderservice.service.TOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author hong
 * @since 2020-08-14
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {

    @Autowired
    private EduClient eduClient;
    @Autowired
    private UcenterClient ucenterClient;
    @Override
    public String saveOrder(String courseId, String memberId) {
        CourseInfoForm courseInfo = eduClient.getCourseInfoById(courseId);
        UcenterMember ucenterMember = ucenterClient.getInfoById(memberId);
        TOrder order = new TOrder();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseCover(courseInfo.getCover());
        order.setCourseTitle(courseInfo.getTitle());
        order.setTeacherName("test");
        order.setTotalFee(courseInfo.getPrice());
        order.setMemberId(memberId);
        order.setMobile(ucenterMember.getMobile());
        order.setNickname(ucenterMember.getNickname());
        order.setStatus(0);
        order.setPayType(1);
        baseMapper.insert(order);
        return order.getOrderNo();
    }
}
