package com.hong.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hong.commonutils.result.R;
import com.hong.eduservice.entity.EduTeacher;
import com.hong.eduservice.query.EduTeacherQuery;
import com.hong.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author hong
 * @since 2020-08-03
 */
@Api(description = "讲师管理")
@CrossOrigin
@RestController
@RequestMapping("/admin/edu/teacher")
public class EduTeacherController {
    @Autowired
    private EduTeacherService eduTeacherService;

    @ApiOperation(value = "返回讲师列表")
    @GetMapping
    public R getList() {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("sort");
        List<EduTeacher> list = eduTeacherService.list(wrapper);
        return R.ok().data("items", list);
    }

    @ApiOperation(value = "根据ID逻辑删除讲师")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name="id",value = "删除讲师ID",required = true)
            @PathVariable String id) {
        boolean res = eduTeacherService.removeById(id);
        if(res){
            return R.ok();
        }else{
            return R.error().message("删除失败");
        }

    }

    @ApiOperation(value = "根据条件分页查询讲师")
    @GetMapping("{page}/{limit}")
    public R pageQuery(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "eduTeacherQuery", value = "查询条件", required = false)
            EduTeacherQuery eduTeacherQuery
    ) {

        Page<EduTeacher> pageParam = new Page<>(page, limit);
        eduTeacherService.pageQuery(pageParam,eduTeacherQuery);
        List<EduTeacher> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "保存讲师信息")
    @PostMapping
    public R save(
            @ApiParam(name = "teacher", value = "讲师", required = true)
            @RequestBody
           EduTeacher eduTeacher) {
        eduTeacherService.save(eduTeacher);
        return R.ok();
    }

    @ApiOperation(value = "根据ID得到讲师信息")
    @GetMapping("{id}")
    public R getById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable
                    String id
    ) {
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok().data("item", eduTeacher);
    }

    @ApiOperation(value = "根据ID修改讲师信息")
    @PutMapping("{id}")
    public R updateById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id,
            @ApiParam(name = "teacher", value = "讲师", required = true)
            @RequestBody EduTeacher eduTeacher
    ) {
        eduTeacher.setId(id);
        eduTeacherService.updateById(eduTeacher);
        return R.ok();
    }
}

