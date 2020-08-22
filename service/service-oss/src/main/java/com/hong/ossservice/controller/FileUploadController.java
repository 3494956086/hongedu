package com.hong.ossservice.controller;

import com.hong.commonutils.result.R;
import com.hong.ossservice.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(description = "文件上传管理")
@CrossOrigin
@RestController
@RequestMapping("/oss/file")
public class FileUploadController {

    @Autowired
    private FileService fileService;
    @ApiOperation(value = "文件上传到阿里云OSS")
    @PostMapping("/upload")
    public R upload(
            @ApiParam(name = "file",value = "上传文件",required = true)
            @RequestParam("file")MultipartFile file,
            @ApiParam(name = "host", value = "文件上传路径", required = false)
           @RequestParam("host") String host
            ){
        String uploadUrl = fileService.upload(file,host);
        return R.ok().message("上传成功").data("url",uploadUrl);
    }
}
