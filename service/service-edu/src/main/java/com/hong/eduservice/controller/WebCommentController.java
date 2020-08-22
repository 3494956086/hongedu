package com.hong.eduservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hong.commonutils.utils.JwtUtils;
import com.hong.commonutils.result.R;
import com.hong.commonutils.result.ResultCode;
import com.hong.eduservice.client.UcenterClient;
import com.hong.eduservice.entity.EduComment;
import com.hong.commonutils.vo.UcenterMember;
import com.hong.eduservice.service.EduCommentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/web/comment")
public class WebCommentController {
    @Autowired
    private EduCommentService eduCommentService;

    @Autowired
    private UcenterClient ucenterClient;

    @ApiOperation(value = "前端返回评论列表")
    @GetMapping("{page}/{limit}")
    public R index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
                   @ApiParam(name = "limit", value = "每页记录数", required = true)
                   @PathVariable Long limit,
                   @ApiParam(name = "courseQuery", value = "查询对象", required = false)
                   String courseId){
        Page<EduComment> myPage = new Page<>(page, limit);
        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        eduCommentService.page(myPage,wrapper);
        List<EduComment> records = myPage.getRecords();
        long size = myPage.getSize();
        long total = myPage.getTotal();
        long pages = myPage.getPages();
        long current = myPage.getCurrent();
        boolean hasNext = myPage.hasNext();
        boolean hasPrevious = myPage.hasPrevious();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("items",records);
        map.put("size",size);
        map.put("total",total);
        map.put("pages",pages);
        map.put("current",current);
        map.put("hasNext",hasNext);
        map.put("hasPrevious",hasPrevious);
       return R.ok().data(map);
    }

    @ApiOperation(value = "评论保存")
    @PostMapping("save")
    public R save(@RequestBody EduComment comment, HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByToken(request);
        if(StringUtils.isEmpty(memberId)){
            return R.error().code(ResultCode.NO_LOGIN).message("请先登录");
        }
        comment.setMemberId(memberId);
        UcenterMember ucenterPay = ucenterClient.getUcenterPay(memberId);
        //从登录模块那里获取nickname和头像
        comment.setNickname(ucenterPay.getNickname());
        comment.setAvatar(ucenterPay.getAvatar());
        eduCommentService.save(comment);
        return R.ok();
    }
}
