package com.hong.aclservice.service;

import com.hong.aclservice.entity.AclRole;
import com.hong.aclservice.entity.AclUser;
import com.baomidou.mybatisplus.extension.service.IService;


import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author hong
 * @since 2020-08-15
 */
public interface AclUserService extends IService<AclUser> {
   AclUser selectByUsername(String username);

}
