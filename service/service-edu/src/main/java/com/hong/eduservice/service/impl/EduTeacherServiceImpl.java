package com.hong.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.util.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hong.eduservice.entity.EduTeacher;
import com.hong.eduservice.mapper.EduTeacherMapper;
import com.hong.eduservice.query.EduTeacherQuery;
import com.hong.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author hong
 * @since 2020-08-03
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public void pageQuery(Page<EduTeacher> pageParam, EduTeacherQuery eduTeacherQuery) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("sort");
        if(eduTeacherQuery==null){
            baseMapper.selectPage(pageParam,wrapper);
            return;
        }
        String name = eduTeacherQuery.getName();
        Integer level = eduTeacherQuery.getLevel();
        String begin = eduTeacherQuery.getBegin();
        String end = eduTeacherQuery.getEnd();
        if(!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);
        }
        baseMapper.selectPage(pageParam,wrapper);
    }

    @Override
    public Map<String, Object> pageListWeb(Page<EduTeacher> pageParam) {
       QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
       wrapper.orderByAsc("sort");
       baseMapper.selectPage(pageParam,wrapper);
        List<EduTeacher> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        long current = pageParam.getCurrent();
        long size = pageParam.getSize();
        long pages = pageParam.getPages();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();
       Map<String,Object> map =  new HashMap<String,Object>();
       map.put("items",records);
       map.put("total",total);
       map.put("current",current);
       map.put("size",size);
       map.put("pages",pages);
       map.put("hasNext",hasNext);
       map.put("hasPrevious",hasPrevious);
       return map;
    }

    @Override
    public boolean removeById(Serializable id) {
        Integer res = baseMapper.deleteById(id);
        return null != res && res>0;
    }
}
