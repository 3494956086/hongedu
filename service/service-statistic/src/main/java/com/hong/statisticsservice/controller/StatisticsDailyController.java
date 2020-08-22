package com.hong.statisticsservice.controller;


import com.hong.commonutils.result.R;
import com.hong.statisticsservice.service.StatisticsDailyService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author hong
 * @since 2020-08-15
 */
@RestController
@RequestMapping("/statistics")
public class StatisticsDailyController {
    @Autowired
    private StatisticsDailyService statisticsDailyService;
    @PostMapping("{day}")
    public R createStatisticsByDate(@PathVariable String day){
        statisticsDailyService.createStatisticsByDay(day);
        return R.ok();
    }

    @GetMapping("show-chart/{begin}/{end}/{type}")
    public R showChart(@PathVariable String begin,@PathVariable String
            end,@PathVariable String type){
        Map<String, Object> map = statisticsDailyService.getChartData(begin, end, type);
        return R.ok().data(map);
    }
}

