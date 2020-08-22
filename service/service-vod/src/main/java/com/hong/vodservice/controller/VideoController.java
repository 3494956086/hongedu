package com.hong.vodservice.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.hong.commonutils.result.R;
import com.hong.vodservice.utils.AliyunVodSDKUtils;
import com.hong.vodservice.utils.ConstantPropertiesUtil;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

@Api(description = "视频点播")
@CrossOrigin
@RestController
@RequestMapping("/vod/video")
public class VideoController {
    @GetMapping("get-play-auth/{videoId}")
    public R getVideoPlayAuth(
            @PathVariable("videoId") String videoId) throws Exception {
        String keyId = ConstantPropertiesUtil.KEY_ID;
        String keySecret = ConstantPropertiesUtil.KEY_SECRET;
        DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(keyId, keySecret);
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId(videoId);
        GetVideoPlayAuthResponse response = client.getAcsResponse(request);
        String playAuth = response.getPlayAuth();
        return R.ok().message("获取凭证成功!").data("playAuth",playAuth);
    }
}
