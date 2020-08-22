package com.hong.statisticsservice.client;

import com.hong.commonutils.result.R;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "service-ucenter",fallback = UcenterServiceImpl.class)
public interface UcenterClient {

          @GetMapping("/ucenter/registerCount/{day}")
          R registerCount(@PathVariable("day") String day);
}
