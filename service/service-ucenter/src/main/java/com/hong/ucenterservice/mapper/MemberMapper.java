package com.hong.ucenterservice.mapper;

import com.hong.ucenterservice.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author hong
 * @since 2020-08-11
 */
public interface MemberMapper extends BaseMapper<Member> {
    Integer selectRegisterCount(String day);
}
