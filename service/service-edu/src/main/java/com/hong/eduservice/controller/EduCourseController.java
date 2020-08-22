package com.hong.eduservice.controller;



import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hong.commonutils.result.R;
import com.hong.commonutils.vo.CourseInfoForm;
import com.hong.eduservice.entity.CourseQuery;
import com.hong.eduservice.entity.EduCourse;
import com.hong.eduservice.entity.vo.CoursePublishVo;
import com.hong.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author hong
 * @since 2020-08-06
 */
@Api(description = "课程管理")
@CrossOrigin
@RestController
@RequestMapping("/admin/edu/course")
public class EduCourseController {
    @Autowired
    private EduCourseService eduCourseService;

    @ApiOperation(value = "保存课程详情")
    @PostMapping("save-course-info")
    public R saveCourseInfo(
            @ApiParam(name="courseInfoForm",value = "课程详情表单",required = true)
            @RequestBody CourseInfoForm courseInfoForm){
        String courseId= eduCourseService.saveCourseInfoForm(courseInfoForm);
        if(!StringUtils.isEmpty(courseId)){
            return R.ok().message("保存成功").data("courseId",courseId);
        }else{
            return R.error().message("保存失败");
        }
    }

    @ApiOperation(value = "根据ID获取课程详情表单")
    @GetMapping("course-info/{id}")
    public R getById(
            @ApiParam(name="id",value = "课程详情id",required = true)
            @PathVariable String id
            ){
        CourseInfoForm courseInfoFormById = eduCourseService.getCourseInfoFormById(id);
        return R.ok().data("item",courseInfoFormById);
    }

    @ApiOperation(value = "根据ID获取课程详情表单")
    @GetMapping("get-course-info/{id}")
    public  CourseInfoForm getCourseInfoById(
            @ApiParam(name="id",value = "课程详情id",required = true)
            @PathVariable String id
    ){
        CourseInfoForm courseInfoFormById = eduCourseService.getCourseInfoFormById(id);
        return courseInfoFormById;
    }

    @ApiOperation(value = "根据ID更新课程详情表单")
    @PutMapping("update-course-info/{id}")
    public R update(
            @ApiParam(name = "id",value = "课程ID",required = true)
            @PathVariable  String id,
            @ApiParam(name = "courseInfoForm",value = "课程详情",required = true)
            @RequestBody  CourseInfoForm courseInfoForm){
        eduCourseService.updateCourseInfoById(courseInfoForm);
        return R.ok().data("courseId",id);
    }

    @ApiOperation(value = "根据ID获取发布课程详情表单")
    @GetMapping("course-publish-info/{id}")
    public R getCoursePublishVoById(
           @ApiParam(name = "id",value = "课程ID",required = true)
            @PathVariable String id){
        CoursePublishVo coursePublishVo = eduCourseService.getCoursePublishVoById(id);
        return R.ok().data("item",coursePublishVo);
    }

    @ApiOperation(value = "根据ID发布课程")
    @GetMapping("publish-course/{id}")
    public R publishCourseById(
            @ApiParam(name = "id",value = "课程ID",required = true)
           @PathVariable String id){
        boolean b = eduCourseService.publishCourseById(id);
        if(b){
            return  R.ok();
        }else{
            return R.error().message("发布失败");
        }
    }

    @ApiOperation(value = "分页课程列表")
    @GetMapping("{page}/{limit}")
    public R pageQuery(
            @ApiParam(name = "page", value = "当前页码", required = true)
          @PathVariable  Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable  Long limit,
            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
            CourseQuery courseQuery){
        Page<EduCourse> pageParam = new Page<>(page, limit);
        eduCourseService.pageQuery(pageParam,courseQuery);
        Long total = pageParam.getTotal();
        List<EduCourse> rows = pageParam.getRecords();
        return R.ok().data("total",total).data("items",rows);
    }

    @ApiOperation(value = "根据ID删除课程")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable
            String id){
        boolean b = eduCourseService.removeCourseById(id);
        if(b){
           return R.ok();
        }else{
            return R.error().message("删除失败");
        }
    }

}

