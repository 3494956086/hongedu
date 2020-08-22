package com.hong.vodservice.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.hong.commonutils.result.ResultCode;
import com.hong.servicebase.exception.HongEduException;
import com.hong.vodservice.service.VideoService;
import com.hong.vodservice.utils.AliyunVodSDKUtils;
import com.hong.vodservice.utils.ConstantPropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@Service
public class VideoServiceImpl implements VideoService {
    @Override
    public String uploadVideo(MultipartFile file) {
        try{
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String title = originalFilename.substring(0,originalFilename.lastIndexOf("."));
            UploadStreamRequest request = new UploadStreamRequest(ConstantPropertiesUtil.KEY_ID,
                    ConstantPropertiesUtil.KEY_SECRET, title,
                    originalFilename, inputStream);
            UploadVideoImpl uploadVideo = new UploadVideoImpl();
            UploadStreamResponse response = uploadVideo.uploadStream(request);
            String videoId = response.getVideoId();
            if(!response.isSuccess()){
                String errorMessage = "阿里云上传错误: code:"+
                        response.getCode()+",message:" +response.getMessage();
             log.warn(errorMessage);
             if(StringUtils.isEmpty(videoId)){
                 throw new HongEduException(ResultCode.ERROR,errorMessage);
             }
            }
            return videoId;
        }catch(IOException e){
           throw new HongEduException(ResultCode.ERROR,"上传失败");
        }

    }

    @Override
    public void removeVideo(String videoId) {

        try {
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(ConstantPropertiesUtil.KEY_ID, ConstantPropertiesUtil.KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(videoId);
            DeleteVideoResponse response = client.getAcsResponse(request);
            System.out.print("RequestId = " + response.getRequestId() + "\n");
        } catch (ClientException e) {
            throw new HongEduException(ResultCode.ERROR,"删除视频失败");
        }
    }

    @Override
    public void removeVideoList(List<String> videoIdList) {
        try {
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(ConstantPropertiesUtil.KEY_ID, ConstantPropertiesUtil.KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            //一次只能删20个
            String str = org.apache.commons.lang.StringUtils.join(videoIdList.toArray(), ",");
            request.setVideoIds(str);
            DeleteVideoResponse response = client.getAcsResponse(request);
            System.out.print("RequestId = " + response.getRequestId() + "\n");
        } catch (ClientException e) {
            throw new HongEduException(ResultCode.ERROR,"删除视频失败");
        }
    }
}
