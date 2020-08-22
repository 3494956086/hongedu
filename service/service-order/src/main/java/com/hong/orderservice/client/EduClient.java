package com.hong.orderservice.client;

import com.hong.commonutils.vo.CourseInfoForm;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "service-edu")
public interface EduClient {
    @ApiOperation(value = "根据ID获取课程详情表单")
    @GetMapping("/admin/edu/course/get-course-info/{id}")
    CourseInfoForm getCourseInfoById(
            @PathVariable("id") String id
    );
}
