package com.hong.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hong.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hong.eduservice.query.EduTeacherQuery;

import java.util.Map;


/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author hong
 * @since 2020-08-03
 */
public interface EduTeacherService extends IService<EduTeacher> {
  void pageQuery(Page<EduTeacher> pageParam, EduTeacherQuery eduTeacherQuery);
  Map<String, Object> pageListWeb(Page<EduTeacher> pageParam);

}
