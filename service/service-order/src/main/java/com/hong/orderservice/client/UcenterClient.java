package com.hong.orderservice.client;

import com.hong.commonutils.vo.UcenterMember;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "service-ucenter")
public interface UcenterClient {
    @GetMapping("/ucenter/getInfoById/{id}")
    UcenterMember getInfoById(@PathVariable("id") String id);

}
