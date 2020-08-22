package com.hong.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hong.commonutils.vo.CourseInfoForm;
import com.hong.eduservice.entity.CourseQuery;
import com.hong.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hong.eduservice.entity.vo.CoursePublishVo;
import com.hong.eduservice.entity.vo.CourseQueryVo;
import com.hong.eduservice.entity.vo.CourseWebVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author hong
 * @since 2020-08-06
 */
public interface EduCourseService extends IService<EduCourse> {
   String saveCourseInfoForm(CourseInfoForm courseInfoForm);
  CourseInfoForm getCourseInfoFormById(String id);
   void updateCourseInfoById(CourseInfoForm courseInfoForm);
    CoursePublishVo getCoursePublishVoById(String id);
    boolean publishCourseById(String id);
    void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery);
    boolean removeCourseById(String id);
    List<EduCourse> selectByTeacherId(String id);
    Map<String, Object> pageListWeb(Page<EduCourse> pageParam, CourseQueryVo courseQuery);
  CourseWebVo getInfoWebById(String id);
  //更新课程浏览数
    void updatePageViewCount(String id);

}
