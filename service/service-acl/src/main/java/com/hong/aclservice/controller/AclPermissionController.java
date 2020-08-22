package com.hong.aclservice.controller;



import com.hong.aclservice.entity.AclPermission;

import com.hong.aclservice.service.AclPermissionService;

import com.hong.commonutils.result.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 权限 前端控制器
 * </p>
 *
 * @author hong
 * @since 2020-08-15
 */
//@CrossOrigin
@RestController
@RequestMapping("/admin/acl/permission")
public class AclPermissionController {
    @Autowired
   private AclPermissionService aclPermissionService;


    @ApiOperation(value = "获取所有权限")
    @GetMapping
    public R indexAllPermissions(){
      List<AclPermission> list =  aclPermissionService.queryAllMenu();
      return R.ok().data("children",list);
    }

    @ApiOperation(value = "删除权限")
    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable String id){
        aclPermissionService.removeChildById(id);
        return R.ok();
    }
    @ApiOperation(value = "更新权限")
    @PutMapping("update")
    public R update(@RequestBody AclPermission permission){
        aclPermissionService.updateById(permission);
        return R.ok();
    }

    @ApiOperation(value = "获取角色ID获取菜单")
    @PostMapping("toAssign/{roleId}")
    public R toAssign(String roleId){
        List<AclPermission> list =  aclPermissionService.selectAllMenu(roleId);
        return R.ok().data("children",list);
    }
    @ApiOperation(value = "给角色分配权限")
    @PostMapping("/doAssign")
    public R doAssign(String roleId,String[] permissionIds){
        aclPermissionService.saveRolePermissionRealtionShip(roleId,permissionIds);
        return R.ok();
    }

    @ApiOperation(value = "新增菜单")
    @PostMapping("save")
    public R save(@RequestBody AclPermission permission) {
        aclPermissionService.save(permission);
        return R.ok();
    }




}

