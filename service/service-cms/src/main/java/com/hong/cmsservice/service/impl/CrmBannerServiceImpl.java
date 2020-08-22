package com.hong.cmsservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hong.cmsservice.entity.CrmBanner;
import com.hong.cmsservice.mapper.CrmBannerMapper;
import com.hong.cmsservice.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheEvict;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


import java.util.List;


/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author hong
 * @since 2020-08-09
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Cacheable(value = "banners",key ="'selectIndexList'" )
    @Override
    public List<CrmBanner> selectIndexList() {
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("sort");
        return baseMapper.selectList(wrapper);
    }


    @Override
    public void pageBanner(Page<CrmBanner> pageParam, Object o) {
        baseMapper.selectPage(pageParam,null);
    }

    @Override
    public CrmBanner getBannerById(String id) {
        return baseMapper.selectById(id);
    }
    @CacheEvict(value = "banner",allEntries = true)
    @Override
    public void saveBanner(CrmBanner banner) {
            baseMapper.insert(banner);
    }
    @CacheEvict(value = "banner",allEntries = true)
    @Override
    public void updateBannerById(CrmBanner banner) {
         baseMapper.updateById(banner);
    }
    @CacheEvict(value = "banner",allEntries = true)
    @Override
    public void removeBannerById(String id) {
          baseMapper.deleteById(id);
    }
}
