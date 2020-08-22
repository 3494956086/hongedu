package com.hong.aclservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hong.aclservice.entity.AclUser;
import com.hong.aclservice.mapper.AclUserMapper;
import com.hong.aclservice.service.AclUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;



/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author hong
 * @since 2020-08-15
 */
@Service
public class AclUserServiceImpl extends ServiceImpl<AclUserMapper, AclUser> implements AclUserService {


    @Override
    public AclUser selectByUsername(String username) {
        QueryWrapper<AclUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        AclUser aclUser = baseMapper.selectOne(wrapper);
        return aclUser;
    }



}
