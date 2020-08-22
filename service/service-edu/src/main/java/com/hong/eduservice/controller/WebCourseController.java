package com.hong.eduservice.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hong.commonutils.result.R;
import com.hong.commonutils.utils.JwtUtils;
import com.hong.eduservice.client.OrderClient;
import com.hong.eduservice.entity.EduCourse;
import com.hong.eduservice.entity.vo.CourseQueryVo;
import com.hong.eduservice.entity.vo.CourseWebVo;
import com.hong.eduservice.entity.vo.EduChapterVo;
import com.hong.eduservice.service.EduChapterService;
import com.hong.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Api(description = "前端课程列表")
@CrossOrigin
@RestController
@RequestMapping("/web/course")
public class WebCourseController {
    @Autowired
    private EduCourseService eduCourseService;
   @Autowired
   private EduChapterService eduChapterService;
   @Autowired
   private OrderClient orderClient;
    @PostMapping(value = "{page}/{limit}")
    public R pageList(@ApiParam(name = "page", value = "当前页码", required = true)
                       @PathVariable Long page,
                      @ApiParam(name = "limit", value = "每页记录数", required = true)
                       @PathVariable Long limit,
                 @RequestBody(required = false) CourseQueryVo courseQueryVo,HttpServletRequest request){
        System.out.println(request.getMethod());
        Page<EduCourse> myPage = new Page<>(page, limit);
        Map<String, Object> map = eduCourseService.pageListWeb(myPage, courseQueryVo);
        return R.ok().data(map);
    }
    @GetMapping("{id}")
    public R getInfoWebById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id, HttpServletRequest request){
        CourseWebVo courseWebVo = eduCourseService.getInfoWebById(id);
        List<EduChapterVo> eduChapterVos = eduChapterService.nestedList(id);
        boolean isCourse = orderClient.isBuyCourse(JwtUtils.getMemberIdByToken(request), id);
        return R.ok().data("course",courseWebVo).data("chapterVoList",eduChapterVos).data("isBuyCourse",isCourse);
    }
}
