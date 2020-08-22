package com.hong.eduservice.mapper;

import com.hong.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hong.eduservice.entity.vo.CoursePublishVo;
import com.hong.eduservice.entity.vo.CourseWebVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author hong
 * @since 2020-08-06
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    CoursePublishVo selectCoursePublishVoById(String id);
    CourseWebVo selectInfoWebById(String courseId);
}
