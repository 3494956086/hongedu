package com.hong.cmsservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hong.cmsservice.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author hong
 * @since 2020-08-09
 */
public interface CrmBannerService extends IService<CrmBanner> {
     List<CrmBanner> selectIndexList();
     void pageBanner(Page<CrmBanner> pageParam, Object o);
     CrmBanner getBannerById(String id);
     void saveBanner(CrmBanner banner);
     void updateBannerById(CrmBanner banner);
     void removeBannerById(String id);
}
