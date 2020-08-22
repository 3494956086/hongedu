package com.hong.aclservice.mapper;

import com.hong.aclservice.entity.AclPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 权限 Mapper 接口
 * </p>
 *
 * @author hong
 * @since 2020-08-15
 */
public interface AclPermissionMapper extends BaseMapper<AclPermission> {
    List<String> selectPermissionValueByUserId(String userId);
    List<String> selectAllPermissionValue();
    List<AclPermission> selectPermissionByUserId(String userId);
}
