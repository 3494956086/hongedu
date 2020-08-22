package com.hong.eduservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "service-order",fallback = OrderClientImpl.class)
public interface OrderClient {
    @GetMapping("/order/order/isBuyCourse/{memberId}/{id}")
    boolean isBuyCourse(@PathVariable("memeberId") String memberId, @PathVariable("id") String id);

}
