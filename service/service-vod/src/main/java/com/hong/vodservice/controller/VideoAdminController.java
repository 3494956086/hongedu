package com.hong.vodservice.controller;

import com.hong.commonutils.result.R;
import com.hong.vodservice.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(description = "视频上传控制器")
@CrossOrigin
@RestController
@RequestMapping("/vod/admin/video")
public class VideoAdminController {
    @Autowired
    private VideoService videoService;

    @ApiOperation(value = "上传视频文件")
    @PostMapping("upload")
    public R uploadVideo(
            @ApiParam(name = "file",value = "上传文件",required = true)
            @RequestBody
            MultipartFile file){
        String s = videoService.uploadVideo(file);
        return R.ok().message("上传视频成功").data("videoId",s);
    }
    @ApiOperation(value = "删除视频文件")
    @DeleteMapping("{videoId}")
    public R removeVideo(
            @ApiParam(name = "videoId",value = "视频ID",required = true)
           @PathVariable String videoId){
        videoService.removeVideo(videoId);
        return R.ok().message("删除视频成功");
    }
    @ApiOperation(value = "批量删除视频文件")
    @DeleteMapping("delete-batch")
    public R removeVideoList(
            @ApiParam(name = "videoIdList",value = "视频IDs",required = true)
           @RequestParam("videoIdList") List<String> videoIdList){
        videoService.removeVideoList(videoIdList);
        return R.ok().message("批量删除成功");
    }
}
