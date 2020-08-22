package com.hong.statisticsservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.hong.statisticsservice.client.UcenterClient;
import com.hong.statisticsservice.entity.StatisticsDaily;
import com.hong.statisticsservice.mapper.StatisticsDailyMapper;
import com.hong.statisticsservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author hong
 * @since 2020-08-15
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {
@Autowired
private UcenterClient ucenterClient;

   @Override
    public void createStatisticsByDay(String day) {
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        baseMapper.delete(wrapper); //删除存在这天的
        Integer countRegister = (Integer) ucenterClient.registerCount(day).getData().get("count");
        Integer countLogin  = RandomUtils.nextInt(100,200);
        Integer countView =  RandomUtils.nextInt(100,200);
        Integer countCourse =  RandomUtils.nextInt(100,200);
        StatisticsDaily statistics = new StatisticsDaily();
        statistics.setLoginNum(countLogin);
        statistics.setCourseNum(countCourse);
        statistics.setDateCalculated(day);
        statistics.setVideoViewNum(countView);
        statistics.setRegisterNum(countRegister);
        baseMapper.insert(statistics);
    }

    @Override
    public Map<String, Object> getChartData(String begin, String end, String type) {
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.select(type,"date_calculated");
        wrapper.between("date_calculated",begin,end);
        List<StatisticsDaily> dailies = baseMapper.selectList(wrapper);
        Map<String,Object> map = new HashMap<String,Object>();
      List<Integer> dataList = new   ArrayList<Integer> ();
      List<String> dateList = new ArrayList<String>();
      map.put("dataList",dataList);
      map.put("dateList",dateList);
      for(int i=0;i<dailies.size();i++){
          StatisticsDaily daily = dailies.get(i);
          dateList.add(daily.getDateCalculated());
          switch (type){
              case "register_num":
                  dataList.add(daily.getRegisterNum());
                  break;
              case "login_num":
                  dataList.add(daily.getLoginNum());
                  break;
              case "video_view_num":
                  dataList.add(daily.getVideoViewNum());
                  break;
              case "course_num":
                  dataList.add(daily.getCourseNum());
                  break;
              default:break;
          }
      }
       return map;
    }
}
