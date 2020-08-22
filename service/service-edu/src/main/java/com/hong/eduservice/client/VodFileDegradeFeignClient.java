package com.hong.eduservice.client;

import com.hong.commonutils.result.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 定义降级方法
 */
@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public R removeVideoById(String id) {
        return R.error().message("超时了");
    }

    @Override
    public R removeVideoList(List<String> videoIdList) {
        return R.error().message("超时了");
    }
}
