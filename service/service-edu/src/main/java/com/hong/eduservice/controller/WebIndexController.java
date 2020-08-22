package com.hong.eduservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hong.commonutils.result.R;
import com.hong.eduservice.entity.EduCourse;
import com.hong.eduservice.entity.EduTeacher;
import com.hong.eduservice.service.EduCourseService;
import com.hong.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "前台查询接口")
@CrossOrigin
@RestController
@RequestMapping("/web/index")
public class WebIndexController {
    @Autowired
    private EduCourseService eduCourseService;
    @Autowired
    private EduTeacherService eduTeacherService;

    @GetMapping("index")
    public R index(){
        QueryWrapper<EduTeacher> wrapper1 = new QueryWrapper<>();
        wrapper1.orderByDesc("id");
        wrapper1.last("limit 4");
        List<EduTeacher> teacherList = eduTeacherService.list(wrapper1);

        QueryWrapper<EduCourse> wrapper2 = new QueryWrapper<>();
        wrapper2.orderByDesc("id");
        wrapper2.last("limit 8");
        List<EduCourse> courseList = eduCourseService.list(wrapper2);

        return R.ok().data("teacherList",teacherList).data("courseList",courseList);
    }
}
