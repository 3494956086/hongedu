package com.hong.orderservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hong.commonutils.utils.JwtUtils;
import com.hong.commonutils.result.R;
import com.hong.orderservice.entity.TOrder;
import com.hong.orderservice.service.TOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author hong
 * @since 2020-08-14
 */
@Api(value = "支付控制器")
@RestController
@RequestMapping("/order/order")
public class TOrderController {
    @Autowired
    private TOrderService tOrderService;

    @ApiOperation(value = "生成订单")
    @PostMapping("createOrder/{courseId}")
    public R createOrder(
            @ApiParam(name = "courseId",value = "课程ID",required = true)
            @PathVariable String courseId, HttpServletRequest request) {
        String orderId = tOrderService.saveOrder(courseId, JwtUtils.getMemberIdByToken(request));
        return R.ok().data("orderId",orderId);
    }
    @ApiOperation(value = "生成订单")
    @GetMapping("getOrder/{orderId}")
    public R get(
            @ApiParam(name = "orderId",value = "订单ID",required = true)
            @PathVariable String orderId){
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        TOrder torder = tOrderService.getOne(wrapper);
        return R.ok().data("item",torder);
    }

    @ApiOperation(value = "是否购买")
    @GetMapping("isBuyCourse/{memberId}/{id}")
    public boolean isBuyCourse(@PathVariable String memberId,@PathVariable String id){
        int count = tOrderService.count(new QueryWrapper<TOrder>()
                .eq("member_id", memberId)
                .eq("course_id", id)
                .eq("status", 1));
        if(count>0){
            return true;
        }else{
            return false;
        }
    }
}

