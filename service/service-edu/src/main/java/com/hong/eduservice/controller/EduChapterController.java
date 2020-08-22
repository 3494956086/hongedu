package com.hong.eduservice.controller;

import com.hong.commonutils.result.R;
import com.hong.eduservice.entity.EduChapter;
import com.hong.eduservice.entity.vo.EduChapterVo;
import com.hong.eduservice.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description = "章节管理")
@RestController
@CrossOrigin
@RequestMapping("/admin/edu/chapter")
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    @ApiOperation(value = "获取章节列表")
    @GetMapping("nested-list/{courseId}")
    public R nestedListByCourseId(
            @ApiParam(name = "courseId",value = "课程ID",required = true)
            @PathVariable
            String courseId
    ){
        List<EduChapterVo> eduChapterVos = eduChapterService.nestedList(courseId);
        return R.ok().data("items",eduChapterVos);
    }

    @ApiOperation(value = "保存章节")
    @PostMapping
    public R save(
            @ApiParam(name = "eduChapter",value = "章节",required = true)
            @RequestBody  EduChapter eduChapter){
        eduChapterService.save(eduChapter);
        return R.ok();
    }
    @ApiOperation(value = "获取章节")
    @GetMapping("{id}")
    public R getById(
            @ApiParam(name = "id",value = "章节id",required = true)
            @PathVariable
            String id){
        EduChapter chapter = eduChapterService.getById(id);
        return R.ok().data("item",chapter);
    }
    @ApiOperation(value = "更新章节")
    @PutMapping("{id}")
    public R updateById(
            @ApiParam(name = "id",value = "章节id",required = true)
           @PathVariable
            String id,
           @ApiParam(name = "chapter",value = "章节",required = true)
           @RequestBody EduChapter chapter){
        chapter.setId(id);
        eduChapterService.updateById(chapter);
        return R.ok();
    }

    @ApiOperation(value = "更新章节")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id",value = "章节id",required = true)
            @PathVariable  String id){
        boolean b = eduChapterService.removeById(id);
        if(b){
            return R.ok();
        }else{
            return R.error().message("删除失败");
        }
    }
}
