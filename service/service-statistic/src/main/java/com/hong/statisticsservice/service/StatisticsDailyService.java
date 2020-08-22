package com.hong.statisticsservice.service;

import com.hong.statisticsservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author hong
 * @since 2020-08-15
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {
     void createStatisticsByDay(String day);

     Map<String, Object> getChartData(String begin, String end, String type);
}
