package com.hong.eduservice.service;

import com.hong.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hong.eduservice.entity.vo.EduSubjectNestedVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author hong
 * @since 2020-08-05
 */
public interface EduSubjectService extends IService<EduSubject> {
void importSubjectData(MultipartFile file,EduSubjectService eduSubjectService);
    List<EduSubjectNestedVo> nestedList();
}
