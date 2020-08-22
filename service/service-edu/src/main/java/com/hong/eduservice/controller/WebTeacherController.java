package com.hong.eduservice.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hong.commonutils.result.R;
import com.hong.eduservice.entity.EduCourse;
import com.hong.eduservice.entity.EduTeacher;
import com.hong.eduservice.service.EduCourseService;
import com.hong.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(value = "前台讲师controller")
@RestController
@CrossOrigin
@RequestMapping("/web/teacher")
public class WebTeacherController {
    @Autowired
    private EduTeacherService eduTeacherService;
    @Autowired
    private EduCourseService eduCourseService;

    @ApiOperation(value = "根据参数返回讲师列表map")
    @GetMapping(value = "{page}/{limit}")
   public R pageList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit ){
        Page<EduTeacher> myPage   =  new Page<>(page,limit);
        Map<String, Object> map = eduTeacherService.pageListWeb(myPage);
        return R.ok().data(map);
    }
    @ApiOperation(value = "根据讲师ID返回课程列表")
    @GetMapping(value = "{id}")
    public R getById(
            @ApiParam(name = "id",value = "id",required = true)
            @PathVariable String id){
        EduTeacher teacher = eduTeacherService.getById(id);
        List<EduCourse> eduCourses = eduCourseService.selectByTeacherId(id);
        return R.ok().data("teacher",teacher).data("courseList",eduCourses);
    }
}
