package com.hong.eduservice.client;

import com.hong.commonutils.vo.UcenterMember;
import org.springframework.stereotype.Component;

/**
 * 定义降级方法
 */
@Component
public class UcenterClientImpl implements UcenterClient{
    @Override
    public UcenterMember getUcenterPay(String memberId) {
        return null;
    }
}
