package com.hong.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hong.commonutils.result.ResultCode;
import com.hong.eduservice.entity.EduChapter;
import com.hong.eduservice.entity.EduVideo;
import com.hong.eduservice.entity.vo.EduChapterVo;
import com.hong.eduservice.entity.vo.EduVideoVo;
import com.hong.eduservice.mapper.EduChapterMapper;
import com.hong.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hong.eduservice.service.EduVideoService;
import com.hong.servicebase.exception.HongEduException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author hong
 * @since 2020-08-06
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
     @Autowired
     private EduVideoService eduVideoService;
    @Override
    public List<EduChapterVo> nestedList(String courseId) {
       ArrayList<EduChapterVo> eduChapterVos = new ArrayList<>() ;

        QueryWrapper<EduChapter> wrapper1 =    new QueryWrapper<>();
        wrapper1.eq("course_id",courseId);
        wrapper1.orderByAsc("sort","id");
        List<EduChapter> eduChapters = baseMapper.selectList(wrapper1);

        QueryWrapper<EduVideo> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("course_id",courseId);
        wrapper2.orderByAsc("sort","id");
        List<EduVideo> videosList = eduVideoService.list(wrapper2);

        for(int i=0;i<eduChapters.size();i++){
            EduChapter eduChapter = eduChapters.get(i);
            EduChapterVo eduChapterVo = new EduChapterVo();
            BeanUtils.copyProperties(eduChapter,eduChapterVo);
            List<EduVideoVo> eduVideoVos = new ArrayList<>();
           for(int j=0;j<videosList.size();j++){
               EduVideo eduVideo = videosList.get(j);
               if(eduVideo.getChapterId().equals(eduChapter.getId())){
                   EduVideoVo eduVideoVo = new EduVideoVo();
                   BeanUtils.copyProperties(eduVideo,eduVideoVo);
                   eduVideoVos.add(eduVideoVo);
               }
           }
            eduChapterVo.setChildren(eduVideoVos);
            eduChapterVos.add(eduChapterVo);
        }
        return eduChapterVos;
    }

    @Override
    public boolean removeChapterById(String id) {

        if(eduVideoService.getCountByChapterId(id)){
            throw new HongEduException(ResultCode.ERROR,"该章节下存在视频教程，请先删除视频教程!");
        }
        Integer i = baseMapper.deleteById(id);

        return null != i && i>0;
    }

    @Override
    public boolean removeByCourseId(String id) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        Integer i = baseMapper.delete(wrapper);
        return null !=i && i>0;
    }
}
