package com.hong.cmsservice.controller;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hong.cmsservice.entity.CrmBanner;
import com.hong.cmsservice.service.CrmBannerService;
import com.hong.commonutils.result.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author hong
 * @since 2020-08-09
 */
@Api(description = "后台管理轮播图")
@CrossOrigin
@RestController
@RequestMapping("/cms/admin/banner")
public class CrmBannerController {
    @Autowired
    private CrmBannerService crmBannerService;

     @ApiOperation(value = "获取所有")
     @GetMapping("{page}/{limit}")
     public R index(@ApiParam(name = "page", value = "当前页码", required = true)
                    @PathVariable Long page,
                    @ApiParam(name = "limit", value = "每页记录数", required = true)
                    @PathVariable Long limit){
         Page<CrmBanner> pageObj = new Page<CrmBanner>(page, limit);
         crmBannerService.pageBanner(pageObj,null);
         return R.ok().data("rows",pageObj.getRecords()).data("total",pageObj.getTotal());
     }

     @ApiOperation(value = "根据ID获取banner")
     @GetMapping("{id}")
     public R getById(
             @ApiParam(name = "id", value = "banner的id", required = true)
             @PathVariable String id){
         CrmBanner crmBanner = crmBannerService.getBannerById(id);
         return R.ok().data("item",crmBanner);
     }
    @ApiOperation(value = "保存轮播图")
    @PostMapping("save")
     public R save(
             @ApiParam(name = "crmBanner", value = "crmBanner", required = true)
             @RequestBody CrmBanner crmBanner){
        crmBannerService.saveBanner(crmBanner);
        return R.ok();
     }
    @ApiOperation(value = "更新轮播图")
    @PutMapping("update")
    public R update(
            @ApiParam(name = "crmBanner", value = "crmBanner", required = true)
            @RequestBody CrmBanner crmBanner){
        crmBannerService.updateBannerById(crmBanner);
        return R.ok();
    }
    @ApiOperation(value = "删除轮播图")
    @DeleteMapping("{id}")
    public R remove(
            @ApiParam(name = "id", value = "id", required = true)
            @PathVariable
            String id){
        crmBannerService.removeBannerById(id);
        return R.ok();
    }
}

