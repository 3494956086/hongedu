package com.hong.cmsservice.controller;

import com.hong.cmsservice.entity.CrmBanner;
import com.hong.cmsservice.service.CrmBannerService;
import com.hong.commonutils.result.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "前台获取轮播图接口")
@RestController
@CrossOrigin
@RequestMapping("/cms/banner")
public class WebCrmBannerController {
    @Autowired
    private CrmBannerService crmBannerService;
    @GetMapping("getAllBanner")
    public R index(){
        List<CrmBanner> list = crmBannerService.selectIndexList();
        return R.ok().data("items",list);
    }
}
