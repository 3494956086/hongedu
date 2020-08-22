package com.hong.aclservice.service;

import com.hong.aclservice.entity.AclRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;



/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hong
 * @since 2020-08-15
 */
public interface AclRoleService extends IService<AclRole> {
    //根据用户获取角色数据

     Map<String, Object> findRoleByUserId(String userId) ;



     void saveUserRoleRealtionShip(String userId, String[] roleIds) ;


     List<AclRole> selectRoleByUserId(String id) ;

}
