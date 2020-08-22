package com.hong.ucenterservice.service.impl;

import com.hong.commonutils.utils.MD5;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hong.commonutils.result.ResultCode;
import com.hong.commonutils.utils.JwtUtils;
import com.hong.servicebase.exception.HongEduException;
import com.hong.ucenterservice.entity.LoginInfo;
import com.hong.ucenterservice.entity.Member;
import com.hong.ucenterservice.entity.vo.LoginVo;
import com.hong.ucenterservice.entity.vo.RegisterVo;
import com.hong.ucenterservice.mapper.MemberMapper;
import com.hong.ucenterservice.service.MemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author hong
 * @since 2020-08-11
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    private RedisTemplate<String,String> template = new RedisTemplate<>();

    @Override
    public String login(LoginVo loginVo) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        if(StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)){
            throw new HongEduException(ResultCode.ERROR,"error");
        }
        Member member = baseMapper.selectOne(new QueryWrapper<Member>().eq("mobile", mobile));
        if(null == member){
            throw new HongEduException(ResultCode.ERROR,"error");
        }

        if(!MD5.encrypt(password).equals(member.getPassword())){
            throw new HongEduException(ResultCode.ERROR,"error");
        }
        if(member.getIsDisabled()){
            throw new HongEduException(ResultCode.ERROR,"error");
        }
        String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());
        return token;
    }

    @Override
    public void register(RegisterVo registerVo) {
        String nickname = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();
        if(StringUtils.isEmpty(nickname)||StringUtils.isEmpty(mobile)||
                StringUtils.isEmpty(password)||StringUtils.isEmpty(code)){
            throw new HongEduException(ResultCode.ERROR,"error");
        }

        String validCode = template.opsForValue().get(mobile);
        if(!validCode.equals(code)){
            throw new HongEduException(ResultCode.ERROR,"error");
        }

        Integer i = baseMapper.selectCount(new QueryWrapper<Member>().eq("mobile", mobile));
        if(i.intValue()>0){
            throw new HongEduException(ResultCode.ERROR,"error");
        }
        Member member = new Member();
        member.setNickname(nickname);
        member.setMobile(mobile);
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        this.save(member);
    }

    @Override
    public LoginInfo getLoginInfo(String memberId) {
        Member member = baseMapper.selectById(memberId);
        LoginInfo loginInfo = new LoginInfo();
        BeanUtils.copyProperties(member,loginInfo);
        return loginInfo;
    }

    @Override
    public Integer countRegisterByDay(String day) {
        return baseMapper.selectRegisterCount(day);
    }
}
