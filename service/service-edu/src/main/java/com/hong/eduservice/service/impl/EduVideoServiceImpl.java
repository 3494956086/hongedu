package com.hong.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hong.commonutils.result.ResultCode;
import com.hong.eduservice.client.VodClient;
import com.hong.eduservice.entity.EduVideo;
import com.hong.eduservice.entity.VideoInfoForm;
import com.hong.eduservice.mapper.EduVideoMapper;
import com.hong.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hong.servicebase.exception.HongEduException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author hong
 * @since 2020-08-06
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;
    @Override
    public boolean getCountByChapterId(String chapterId) {
       QueryWrapper<EduVideo> wrapper =  new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        Integer i = baseMapper.selectCount(wrapper);
        return null !=i && i>0;
    }

    @Override
    public void saveVideoInfo(VideoInfoForm videoInfoForm) {
        EduVideo eduVideo = new EduVideo();
        BeanUtils.copyProperties(videoInfoForm,eduVideo);
        boolean b = this.save(eduVideo);
        if(!b){
            throw new HongEduException(ResultCode.ERROR,"课时信息保存失败");
        }
    }

    @Override
    public VideoInfoForm getVideoInfoFormById(String id) {
        EduVideo eduVideo = baseMapper.selectById(id);
        if(eduVideo==null){
            throw new HongEduException(ResultCode.ERROR,"课时数据不存在");
        }
        VideoInfoForm videoInfoForm = new VideoInfoForm();
        BeanUtils.copyProperties(eduVideo,videoInfoForm);
        return videoInfoForm;
    }

    @Override
    public void updateVideoInfoById(VideoInfoForm videoInfoForm) {
        EduVideo eduVideo = new EduVideo();
        BeanUtils.copyProperties(videoInfoForm,eduVideo);
        boolean b = this.updateById(eduVideo);
        if(!b){
            throw new HongEduException(ResultCode.ERROR,"更新课时信息失败");
        }
    }

    @Override
    public boolean removeVideoById(String id) {
        EduVideo eduVideo = baseMapper.selectById(id);
        String sourceId = eduVideo.getVideoSourceId();
        if(!StringUtils.isEmpty(sourceId)){
            vodClient.removeVideoById(sourceId);
        }

        Integer i = baseMapper.deleteById(id);
        return null != i && i>0;
    }

    @Override
    public boolean removeByCourseId(String id) {
        //删除云视频
        QueryWrapper<EduVideo> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("course_id",id);
        wrapper1.select("video_source_id");
        List<EduVideo> eduVideos = baseMapper.selectList(wrapper1);
        ArrayList<String> ids = new ArrayList<>();
        for(int i= 0;i<eduVideos.size();i++){
            String videoSourceId = eduVideos.get(i).getVideoSourceId();
            if(!StringUtils.isEmpty(videoSourceId)){
                ids.add(videoSourceId);
            }
        }
        if(ids.size()>0){
            vodClient.removeVideoList(ids);
        }
        //删除video数据
        QueryWrapper<EduVideo> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("course_id",id);
        Integer i = baseMapper.delete(wrapper2);
        return null !=i && i>0;
    }
}
