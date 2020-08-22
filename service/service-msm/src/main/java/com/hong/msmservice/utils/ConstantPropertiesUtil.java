package com.hong.msmservice.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class ConstantPropertiesUtil implements InitializingBean {

    @Value("${aliyun.oss.file.keyid}")
    private String keyid;
    @Value("${aliyun.oss.file.keysecret}")
    private String keysecret;
    @Value("${aliyun.msm.product}")
    private String product;
    @Value("${aliyun.msm.regionid}")
    private String regionid;
    @Value("${aliyun.msm.domain}")
    private String domain;
    @Value("${aliyun.msm.version}")
    private String version;
    @Value("${aliyun.msm.action}")
    private String action;
    @Value("${aliyun.msm.signname}")
    private String signname;
    @Value("${aliyun.msm.signsource}")
    private String signsource;
    @Value("${aliyun.msm.templatecode}")
    private String templatecode;

    public  static String KEY_ID;
    public static String KEY_SECRET;
    public static String PRODUCT;
    public static String REGION_ID;
    public static String DOMAIN;
    public static String VERSION;
    public static String ACTION;
    public static String SIGNNAME;
    public static String SIGNSOURCE;
    public static String TEMPLATECODE;


    @Override
    public void afterPropertiesSet() throws Exception {
        KEY_ID = this.keyid;
        KEY_SECRET = this.keysecret;
        PRODUCT = this.product;
        REGION_ID = this.regionid;
        DOMAIN = this.domain;
        VERSION = this.version;
        ACTION = this.action;
        SIGNNAME = this.signname;
        SIGNSOURCE = this.signsource;
        TEMPLATECODE = this.templatecode;
    }
}
