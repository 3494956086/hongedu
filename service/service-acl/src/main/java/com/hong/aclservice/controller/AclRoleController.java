package com.hong.aclservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hong.aclservice.entity.AclRole;
import com.hong.aclservice.service.AclRoleService;
import com.hong.commonutils.result.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.util.List;


/**
 * <p>
 *  角色控制器
 * </p>
 *
 * @author hong
 * @since 2020-08-15
 */
//@CrossOrigin
@RestController
@RequestMapping("/admin/acl/role")
public class AclRoleController {
    @Autowired
    private AclRoleService aclRoleService;
    @ApiOperation(value = "分页获取所有角色")
    @GetMapping("{page}/{limit}")
   public R getPageList(@PathVariable Long page, @PathVariable Long limit,
                         AclRole searchObj) {
        Page<AclRole> myPage = new Page<>(page, limit);
        QueryWrapper<AclRole> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(searchObj.getRoleName())){
            wrapper.like("role_name",searchObj.getRoleName());
        }
        aclRoleService.page(myPage,wrapper);
        return R.ok().data("items",myPage.getRecords()).data("total",myPage.getTotal());
    }
    @ApiOperation(value = "根据ID获取角色")
    @GetMapping("get/{id}")
   public R getById(@PathVariable String id) {
        AclRole item = aclRoleService.getById(id);
        return R.ok().data("item",item);
    }
    @ApiOperation(value = "新增角色")
    @PostMapping("save")
    public R save(@RequestBody AclRole role) {
       if( aclRoleService.count(new QueryWrapper<AclRole>().eq("role_name",role.getRoleName()))>0)
           return R.error().message("该用户已存在");
        aclRoleService.save(role);
        return R.ok();
    }
    @ApiOperation(value = "更新角色")
   @PutMapping("update")
   public R updateById(@RequestBody AclRole role) {
        if( aclRoleService.count(new QueryWrapper<AclRole>().eq("role_name",role.getRoleName()))>0)
            return R.error().message("该用户已存在");
        aclRoleService.updateById(role);
        return R.ok();
    }


    @ApiOperation(value = "删除角色")
    @DeleteMapping("remove/{id}")
    public R removeById(@PathVariable String id){
        aclRoleService.removeById(id);
        return R.ok();
    }
    @ApiOperation(value = "批量删除角色")
    @DeleteMapping("batchRemove")
    public R removeRows(@PathVariable List idList){
       aclRoleService.removeByIds(idList);
       return R.ok();
    }
}

