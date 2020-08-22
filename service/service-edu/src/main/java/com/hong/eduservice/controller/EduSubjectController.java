package com.hong.eduservice.controller;

import com.hong.commonutils.result.R;
import com.hong.eduservice.entity.vo.EduSubjectNestedVo;
import com.hong.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(value = "课程分类管理")
@CrossOrigin
@RestController
@RequestMapping("/admin/edu/subject")
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;
    @ApiOperation(value = "课程分类导入")
    @PostMapping("import")
    public R addSubject(MultipartFile file){
        eduSubjectService.importSubjectData(file,eduSubjectService);
        return R.ok();
    }

    @ApiOperation(value = "课程分类列表")
    @GetMapping("")
    public R nestedList(){
        List<EduSubjectNestedVo> eduSubjectNestedVos = eduSubjectService.nestedList();
        return R.ok().data("items",eduSubjectNestedVos);
    }


}
