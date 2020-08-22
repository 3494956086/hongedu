package com.hong.ucenterservice.controller;


import com.hong.commonutils.utils.JwtUtils;
import com.hong.commonutils.result.R;
import com.hong.commonutils.result.ResultCode;
import com.hong.commonutils.vo.UcenterMember;
import com.hong.servicebase.exception.HongEduException;
import com.hong.ucenterservice.entity.LoginInfo;
import com.hong.ucenterservice.entity.Member;
import com.hong.ucenterservice.entity.vo.LoginVo;
import com.hong.ucenterservice.entity.vo.RegisterVo;
import com.hong.ucenterservice.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author hong
 * @since 2020-08-11
 */
@Api(value = "用户控制器")
@RestController
@RequestMapping("/ucenter")
@CrossOrigin
public class MemberController {
    @Autowired
    private MemberService memberService;
    @ApiOperation(value = "会员登录")
    @PostMapping("login")
   public R login(
           @ApiParam(name = "loginVo",value = "登录表单",required = true)
           @RequestBody LoginVo loginVo){
       String token = memberService.login(loginVo);
       return R.ok().data("token",token);
   }
    @ApiOperation(value = "会员注册")
    @PostMapping("register")
    public R register(
            @ApiParam(name = "registerVo",value = "注册表单",required = true)
            @RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok();
    }
    @ApiOperation(value = "根据token获取登录信息")
    @GetMapping("getLoginInfo")
    public R getLoginInfo(HttpServletRequest request){
        try{
            String memberId= JwtUtils.getMemberIdByToken(request);
            LoginInfo loginInfo = memberService.getLoginInfo(memberId);
            return R.ok().data("item",loginInfo);
        }catch(Exception e){
            e.printStackTrace();
            throw new HongEduException(ResultCode.ERROR,"error");
        }

    }
    @ApiOperation(value = "根据token获取登录信息")
    @GetMapping("getInfoById/{id}")
    public UcenterMember getInfoById(@PathVariable String id){
        Member member = memberService.getById(id);
        UcenterMember memberVo = new UcenterMember();
        BeanUtils.copyProperties(member,memberVo);
        return memberVo;
    }

    @ApiOperation(value = "计算注册人数")
    @GetMapping("registerCount/{day}")
    public R registerCount(
            @ApiParam(name = "day",value = "日期",required = true)
            @PathVariable String day){
        Integer count = memberService.countRegisterByDay(day);
        return R.ok().data("count",count);
    }
}

