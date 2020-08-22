package com.hong.eduservice.client;

import com.hong.commonutils.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "service-vod",fallback = VodFileDegradeFeignClient.class)
@Component
public interface VodClient {
    @DeleteMapping(value = "/admin/vod/video/{videoId}")
    R removeVideoById(@PathVariable("videoId") String videoId);
    @DeleteMapping(value = "/admin/vod/video/delete-batch")
    R removeVideoList(@RequestParam("videoIdList") List<String> videoIdList);
}
