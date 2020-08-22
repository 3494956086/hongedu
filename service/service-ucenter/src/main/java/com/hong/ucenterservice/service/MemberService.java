package com.hong.ucenterservice.service;

import com.hong.ucenterservice.entity.LoginInfo;
import com.hong.ucenterservice.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hong.ucenterservice.entity.vo.LoginVo;
import com.hong.ucenterservice.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author hong
 * @since 2020-08-11
 */
public interface MemberService extends IService<Member> {
     String login(LoginVo loginVo);
     void register(RegisterVo registerVo);
     LoginInfo getLoginInfo(String memberId);
     Integer countRegisterByDay(String day);
}
