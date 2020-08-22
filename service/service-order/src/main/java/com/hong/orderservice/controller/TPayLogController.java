package com.hong.orderservice.controller;


import com.hong.commonutils.result.R;
import com.hong.commonutils.result.ResultCode;
import com.hong.orderservice.service.TPayLogService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author hong
 * @since 2020-08-14
 */
@RestController
@RequestMapping("/order/paylog")
public class TPayLogController {
    @Autowired
    private TPayLogService tPayLogService;

    @ApiOperation(value = "根据订单流水号创建二维码")
    @GetMapping("/createnative/{orderNo}")
    public R createNative(
            @ApiParam(name = "orderNo",value ="订单流水号" ,required = true)
            @PathVariable String orderNo){
        Map map = tPayLogService.createNative(orderNo);
        return R.ok().data(map);
    }

    @ApiOperation(value = "根据订单流水号查询支付状态")
    @GetMapping("/queryPayStatus/{orderNo}")
    public R queryPayStatus(
            @ApiParam(name = "orderNo",value = "支付流水号",required = true)
            @PathVariable String orderNo){
        Map map = tPayLogService.queryPayStatus(orderNo);
        if(map==null){
            return R.error().message("支付出错");
        }
        if(map.get("trade_state").equals("SUCCESS")){
            return R.ok().message("支付成功");
        }
        return R.ok().code(ResultCode.PAYING).message("支付中");
    }
}

