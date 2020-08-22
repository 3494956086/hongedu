package com.hong.aclservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hong.aclservice.entity.AclUser;
import com.hong.aclservice.service.AclRoleService;
import com.hong.aclservice.service.AclUserService;
import com.hong.commonutils.result.R;
import com.hong.commonutils.utils.MD5;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author hong
 * @since 2020-08-15
 */
//@CrossOrigin
@RestController
@RequestMapping("/admin/acl/user")
public class AclUserController {
 @Autowired
 private AclUserService aclUserService;
 @Autowired
 private AclRoleService aclRoleService;
  @ApiOperation(value = "分页查询")
    @GetMapping("{page}/{limit}")
    public R getPageList(@PathVariable Long page,@PathVariable Long limit,
                 AclUser searchObj) {
        Page<AclUser> myPage = new Page<>(page,limit);
        QueryWrapper<AclUser> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(searchObj.getUsername())){
           wrapper.like("username",searchObj.getUsername());
        }
        aclUserService.page(myPage,wrapper);
        return R.ok().data("items",myPage.getRecords()).data("total",myPage.getTotal());
    }
    @ApiOperation(value = "根据ID获取用户")
    @GetMapping("get/{id}")
    public R getById(@PathVariable String id) {
        AclUser user = aclUserService.getById(id);
        return R.ok().data("item",user);
    }
    @ApiOperation(value="保存用户")
    @PostMapping("save")
    public R save(@RequestBody AclUser aclUser) {
        if(aclUserService.count(new QueryWrapper<AclUser>().eq("username",aclUser.getUsername()))>0)
            return R.error().message("用户已存在");
        aclUser.setPassword(MD5.encrypt(aclUser.getPassword()));
        aclUserService.save(aclUser);
        return R.ok();
    }
    @ApiOperation(value="更新用户")
    @PutMapping("update")
    public R updateById(@RequestBody AclUser user) {
       if(aclUserService.count(new QueryWrapper<AclUser>().eq("username",user.getUsername()))>0)
           return R.error().message("用户已存在");
        aclUserService.updateById(user);
        return R.ok();
    }
    @DeleteMapping("/remove/{id}")
    public R removeById(@PathVariable String id){
        aclUserService.removeById(id);
        return R.ok();
    }
    @DeleteMapping("batchRemove")
    public R removeRows(@RequestBody List<String> idList){
        aclUserService.removeByIds(idList);
        return R.ok();
    }
    @ApiOperation(value = "根据用户ID获取角色数据")
    @GetMapping("/toAssign/{userId}")
    public R toAssign(@PathVariable  String userId){
        Map<String, Object> map = aclRoleService.findRoleByUserId(userId);
        return R.ok().data(map);
    }
    @ApiOperation(value = "根据用户分配角色")
    @PostMapping("/doAssign")
    public R doAssign(@RequestParam  String userId,@RequestParam String[] roleId){
      aclRoleService.saveUserRoleRealtionShip(userId,roleId);
         return R.ok();
    }


}

