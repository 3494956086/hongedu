package com.hong.eduservice.service;

import com.hong.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hong.eduservice.entity.VideoInfoForm;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author hong
 * @since 2020-08-06
 */
public interface EduVideoService extends IService<EduVideo> {
    boolean getCountByChapterId(String chapterId);
    void saveVideoInfo(VideoInfoForm videoInfoForm);
    VideoInfoForm getVideoInfoFormById(String id);
    void updateVideoInfoById(VideoInfoForm videoInfoForm);
    boolean removeVideoById(String id);
    boolean removeByCourseId(String id);
}
