package com.hong.eduservice.client;

import com.hong.commonutils.vo.UcenterMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name="service-ucenter",fallback =UcenterClientImpl.class )
public interface UcenterClient {
    @GetMapping("/ucenter/getInfoById/{id}")
    UcenterMember getUcenterPay(@PathVariable("id") String memberId);
}
