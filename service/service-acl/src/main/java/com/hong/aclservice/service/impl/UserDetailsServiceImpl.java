package com.hong.aclservice.service.impl;

import com.hong.aclservice.entity.AclUser;
import com.hong.aclservice.service.AclPermissionService;
import com.hong.aclservice.service.AclUserService;
import com.hong.commonutils.result.ResultCode;
import com.hong.security.entity.SecurityUser;
import com.hong.security.entity.User;
import com.hong.servicebase.exception.HongEduException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 自定义用户认证类
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private AclUserService aclUserService;
    @Autowired
    private AclPermissionService aclPermissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AclUser user = aclUserService.selectByUsername(username);
        if (user == null) {
            throw new HongEduException(ResultCode.NO_USER, "找不到用户");
        }

        User myUser = new User();
        BeanUtils.copyProperties(user, myUser);
        List<String> authorities = aclPermissionService.selectPermissionValueByUserId(user.getId());
        SecurityUser securityUser = new SecurityUser(myUser);
        securityUser.setPermissionValueList(authorities);
        return securityUser;
    }
}
