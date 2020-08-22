package com.hong.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hong.commonutils.result.ResultCode;
import com.hong.commonutils.vo.CourseInfoForm;
import com.hong.eduservice.entity.CourseQuery;
import com.hong.eduservice.entity.EduCourse;
import com.hong.eduservice.entity.EduCourseDescription;
import com.hong.eduservice.entity.vo.CoursePublishVo;
import com.hong.eduservice.entity.vo.CourseQueryVo;
import com.hong.eduservice.entity.vo.CourseWebVo;
import com.hong.eduservice.mapper.EduCourseMapper;
import com.hong.eduservice.service.EduChapterService;
import com.hong.eduservice.service.EduCourseDescriptionService;
import com.hong.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hong.eduservice.service.EduVideoService;
import com.hong.servicebase.exception.HongEduException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author hong
 * @since 2020-08-06
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {


    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;
    @Autowired
    private EduVideoService eduVideoService;
    @Autowired
    private EduChapterService eduChapterService;

    @Override
    public String saveCourseInfoForm(CourseInfoForm courseInfoForm) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setStatus(EduCourse.COURSE_DRAFT);
        BeanUtils.copyProperties(courseInfoForm, eduCourse);
        boolean b1 = this.save(eduCourse);
        if (!b1) {
            throw new HongEduException(ResultCode.ERROR, "保存失败");
        }
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoForm.getDescription());
        eduCourseDescription.setId(courseInfoForm.getId());
        boolean b2 = eduCourseDescriptionService.save(eduCourseDescription);
        if (!b2) {
            throw new HongEduException(ResultCode.ERROR, "保存失败");
        }
        return eduCourse.getId();
    }

    @Override
    public CourseInfoForm getCourseInfoFormById(String id) {
        EduCourse course = baseMapper.selectById(id);
        if (course == null) {
            throw new HongEduException(ResultCode.ERROR, "获取失败");
        }
        CourseInfoForm courseInfoForm = new CourseInfoForm();
        BeanUtils.copyProperties(course, courseInfoForm);
        EduCourseDescription courseDescription = eduCourseDescriptionService.getById(id);
        if (courseDescription != null) {
            courseInfoForm.setDescription(courseDescription.getDescription());
        }
        return courseInfoForm;
    }

    @Override
    public void updateCourseInfoById(CourseInfoForm courseInfoForm) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoForm,eduCourse);
        boolean b1 = this.updateById(eduCourse);
        if(!b1){
            throw new HongEduException(ResultCode.ERROR,"更新course失败");
        }
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(eduCourse.getId());
        eduCourseDescription.setDescription(courseInfoForm.getDescription());
        boolean b2 = eduCourseDescriptionService.saveOrUpdate(eduCourseDescription);
        if(!b2){
            throw new HongEduException(ResultCode.ERROR,"更新CourseDescription失败");
        }

    }

    @Override
    public CoursePublishVo getCoursePublishVoById(String id) {
        return baseMapper.selectCoursePublishVoById(id);
    }

    @Override
    public boolean publishCourseById(String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus(EduCourse.COURSE_NORMAL);
        Integer i = baseMapper.updateById(eduCourse);
        return null !=i && i>0;
    }

    @Override
    public void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery) {
       QueryWrapper<EduCourse> wrapper =  new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create");
       if(courseQuery==null){
            baseMapper.selectPage(pageParam,wrapper);
            return;
       }
        String title = courseQuery.getTitle();
        String subjectId = courseQuery.getSubjectId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String teacherId = courseQuery.getTeacherId();
        if(!StringUtils.isEmpty(title)){
            wrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(subjectId)){
            wrapper.eq("subject_id",subjectId);
        }
        if(!StringUtils.isEmpty(subjectParentId)){
            wrapper.eq("subject_parent_id",subjectParentId);
        }
        if(!StringUtils.isEmpty(teacherId)){
            wrapper.eq("teacher_id",teacherId);
        }
        baseMapper.selectPage(pageParam,wrapper);

    }

    @Override
    public boolean removeCourseById(String id) {
        eduVideoService.removeByCourseId(id);
        eduChapterService.removeByCourseId(id);
        eduCourseDescriptionService.removeById(id);
        Integer i = baseMapper.deleteById(id);
        return null !=i && i>0;
    }

    @Override
    public List<EduCourse> selectByTeacherId(String id) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",id);
        wrapper.orderByDesc("gmt_modified");
        List<EduCourse> eduCourses = baseMapper.selectList(wrapper);
        return eduCourses;
    }

    @Override
    public Map<String, Object> pageListWeb(Page<EduCourse> pageParam, CourseQueryVo courseQuery) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(courseQuery.getSubjectParentId())){
            wrapper.eq("subject_parent_id",courseQuery.getSubjectParentId());
        }
        if(!StringUtils.isEmpty(courseQuery.getSubjectId())){
            wrapper.eq("subject_id",courseQuery.getSubjectId());
        }
        if(!StringUtils.isEmpty(courseQuery.getBuyCountSort())){
            wrapper.orderByDesc("buy_count");
        }
        if(!StringUtils.isEmpty(courseQuery.getGmtCreateSort())){
            wrapper.orderByDesc("gmt_create");
        }
        if(!StringUtils.isEmpty(courseQuery.getPriceSort())){
            wrapper.orderByDesc("price");
        }
        baseMapper.selectPage(pageParam, wrapper);
        List<EduCourse> records = pageParam.getRecords();
        long size = pageParam.getSize();
        long current = pageParam.getCurrent();
        long total = pageParam.getTotal();
        long pages = pageParam.getPages();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();
        Map<String,Object> map = new HashMap<>();
        map.put("size",size);
        map.put("current",current);
        map.put("total",total);
        map.put("pages",pages);
        map.put("hasNext",hasNext);
        map.put("hasPrevious",hasPrevious);
        map.put("items",records);
        return map;
    }

    @Override
    public CourseWebVo getInfoWebById(String id) {
        this.updatePageViewCount(id);
        return baseMapper.selectInfoWebById(id);
    }

    /**
     * 更新课程浏览数
     * @param id
     */
    @Override
    public void updatePageViewCount(String id) {
        EduCourse eduCourse = baseMapper.selectById(id);
        eduCourse.setViewCount(eduCourse.getViewCount()+1);
        baseMapper.updateById(eduCourse);
    }


}
