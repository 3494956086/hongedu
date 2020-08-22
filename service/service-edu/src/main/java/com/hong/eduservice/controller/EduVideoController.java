package com.hong.eduservice.controller;


import com.hong.commonutils.result.R;
import com.hong.commonutils.result.ResultCode;
import com.hong.eduservice.entity.VideoInfoForm;
import com.hong.eduservice.service.EduVideoService;
import com.hong.servicebase.exception.HongEduException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author hong
 * @since 2020-08-06
 */
@CrossOrigin
@RestController
@RequestMapping("/admin/edu/video")
public class EduVideoController {
    @Autowired
    private EduVideoService eduVideoService;
    @ApiOperation(value = "保存视频")
    @PostMapping("save-video-info")
    public R save(
          @ApiParam(name ="videoInfoForm",value = "视频表单",required = true)
           @RequestBody VideoInfoForm videoInfoForm){
        eduVideoService.saveVideoInfo(videoInfoForm);
        return R.ok();
    }
    @ApiOperation(value = "根据ID得到课时信息")
    @GetMapping("video-info/{id}")
    public R getVideoInfoById(
            @ApiParam(name="id",value = "课时ID",required = true)
           @PathVariable String id){
        VideoInfoForm videoInfoForm = eduVideoService.getVideoInfoFormById(id);
      return R.ok().data("item",videoInfoForm);
    }

    @ApiOperation(value = "根据课时表单更新课时信息")
    @PutMapping("update-video-info/{id}")
    public R updateVideoInfoById(
            @ApiParam(name="id",value = "课时ID",required = true)
            @PathVariable
            String id,
            @ApiParam(name="videoInfoForm",value = "课时表单",required = true)
            @RequestBody
            VideoInfoForm videoInfoForm){
        eduVideoService.updateVideoInfoById(videoInfoForm);
        return R.ok();
    }
    @ApiOperation(value = "根据课时ID删除课时")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name="id",value = "课时表单id",required = true)
            @PathVariable  String id){
        boolean b = eduVideoService.removeVideoById(id);
        if(b){
            return R.ok();
        }else{
            throw new HongEduException(ResultCode.ERROR,"删除课时失败");
        }
    }

}

