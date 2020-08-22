package com.hong.ossservice.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.hong.commonutils.result.ResultCode;
import com.hong.ossservice.service.FileService;
import com.hong.ossservice.utils.ConstantPropertiesUtil;
import com.hong.servicebase.exception.HongEduException;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String upload(MultipartFile file,String host) {
        String endPoint = ConstantPropertiesUtil.END_POINT;
        String keyId = ConstantPropertiesUtil.KEY_ID;
        String keySecret = ConstantPropertiesUtil.KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;
        String uploadUrl = null;
        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build("http://"+endPoint, keyId, keySecret);
            if (!ossClient.doesBucketExist(bucketName)) {
                ossClient.createBucket(bucketName);
                ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
            }
            InputStream is = file.getInputStream();
            String filePath = new DateTime().toString("yyyy/MM/dd");
            //文件名：uuid.扩展名
            String original = file.getOriginalFilename();
            String fileName = UUID.randomUUID().toString();
            String fileType = original.substring(original.lastIndexOf("."));
            String newName = fileName + fileType;
            if(!StringUtils.isEmpty(filePath)){
                filePath = host+"/"+filePath;
            }
            String fileUrl = filePath + "/" + newName;

            ossClient.putObject(bucketName, fileUrl, is);


            ossClient.shutdown();
            uploadUrl = "https://" + bucketName +"."+endPoint + "/" + fileUrl;
        } catch (IOException e) {
            throw new HongEduException(ResultCode.FILE_UPLOAD_ERROR,e.getMessage());
        }
        return uploadUrl;
    }
}
