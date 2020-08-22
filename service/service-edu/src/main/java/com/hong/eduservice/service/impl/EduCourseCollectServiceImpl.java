package com.hong.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hong.eduservice.entity.EduCourseCollect;
import com.hong.eduservice.mapper.EduCourseCollectMapper;
import com.hong.eduservice.service.EduCourseCollectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程收藏 服务实现类
 * </p>
 *
 * @author hong
 * @since 2020-08-06
 */
@Service
public class EduCourseCollectServiceImpl extends ServiceImpl<EduCourseCollectMapper, EduCourseCollect> implements EduCourseCollectService {

    @Override
    public boolean isPayByMemberId(String courseId,String memberId) {
        QueryWrapper<EduCourseCollect> wrapper = new QueryWrapper<>();
        wrapper.eq("is_deleted","0");
        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",memberId);
        Integer i = baseMapper.selectCount(wrapper);
        return i!=null&& i==1;
    }
}
