package com.hong.eduservice.service;

import com.hong.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hong.eduservice.entity.vo.EduChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author hong
 * @since 2020-08-06
 */
public interface EduChapterService extends IService<EduChapter> {
    List<EduChapterVo> nestedList(String courseId);
    boolean removeChapterById(String id);
    boolean removeByCourseId(String id);
}
