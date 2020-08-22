package com.hong.orderservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.wxpay.sdk.WXPayUtil;
import com.hong.commonutils.utils.HttpClient;
import com.hong.orderservice.entity.TOrder;
import com.hong.orderservice.entity.TPayLog;
import com.hong.orderservice.mapper.TPayLogMapper;
import com.hong.orderservice.service.TOrderService;
import com.hong.orderservice.service.TPayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author hong
 * @since 2020-08-14
 */
@Service
public class TPayLogServiceImpl extends ServiceImpl<TPayLogMapper, TPayLog> implements TPayLogService {
    @Autowired
    private TOrderService tOrderService;


    @Override
    public Map createNative(String orderNo) {
        try{
            QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
            wrapper.eq("order_no",orderNo);
            TOrder tOrder = tOrderService.getOne(wrapper);
            HashMap map = new HashMap();
            //1、设置支付参数
            map.put("appid", "wx74862e0dfcf69954");
            map.put("mch_id", "1558950191");
            map.put("nonce_str", WXPayUtil.generateNonceStr());
            map.put("body", tOrder.getCourseTitle());
            map.put("out_trade_no", orderNo);
            map.put("total_fee", tOrder.getTotalFee().multiply(new
                    BigDecimal("100")).longValue()+"");
            map.put("spbill_create_ip", "127.0.0.1");
            map.put("notify_url",
                    "http://guli.shop/api/order/weixinPay/weixinNotify\n");
            map.put("trade_type", "NATIVE");

           //2、HTTPClient来根据URL访问第三方接口并且传递参数
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            //client设置参数
            client.setXmlParam(WXPayUtil.generateSignedXml(map,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
            //3、返回第三方的数据
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            Map mmap = new HashMap();
            mmap.put("out_trade_no", orderNo);
            mmap.put("course_id", tOrder.getCourseId());
            mmap.put("total_fee", tOrder.getTotalFee());
            mmap.put("result_code", resultMap.get("result_code"));
            mmap.put("code_url", resultMap.get("code_url"));
            return mmap;
        }catch (Exception e){
            e.printStackTrace();
            return new HashMap<>();
        }
 }

    @Override
    public void updateOrderStatus(Map<String, String> map) {
        String out_trade_no = map.get("out_trade_no");

        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",out_trade_no);
        TOrder order = tOrderService.getOne(wrapper);
        if(order.getStatus().intValue()==1)return;  //已经支付了
        order.setStatus(1);
        tOrderService.updateById(order);
        //记录支付日志
        TPayLog tPayLog = new TPayLog();
        tPayLog.setOrderNo(order.getOrderNo());//支付订单号
        tPayLog.setPayTime(new Date());
        tPayLog.setPayType(1);//支付类型
        tPayLog.setTotalFee(order.getTotalFee());//总金额(分)
        tPayLog.setTradeState(map.get("trade_state"));//支付状态
        tPayLog.setTransactionId(map.get("transaction_id"));
        tPayLog.setAttr(JSONObject.toJSONString(map));
        baseMapper.insert(tPayLog);//插入到支付日志表
    }

    @Override
    public Map queryPayStatus(String orderNo) {
        try{
            Map m = new HashMap<>();
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("out_trade_no", orderNo);
            m.put("nonce_str", WXPayUtil.generateNonceStr());
           //2、设置请求
            HttpClient client = new
                    HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(m,
                    "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
           //3、返回第三方的数据
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            return resultMap;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
