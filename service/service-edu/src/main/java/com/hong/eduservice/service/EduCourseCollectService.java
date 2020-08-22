package com.hong.eduservice.service;

import com.hong.eduservice.entity.EduCourseCollect;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程收藏 服务类
 * </p>
 *
 * @author hong
 * @since 2020-08-06
 */
public interface EduCourseCollectService extends IService<EduCourseCollect> {
       boolean isPayByMemberId(String courseId,String memberId);
}
