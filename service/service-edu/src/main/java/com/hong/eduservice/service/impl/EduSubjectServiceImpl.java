package com.hong.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hong.commonutils.result.ResultCode;
import com.hong.eduservice.config.SubjectExcelListener;
import com.hong.eduservice.entity.EduSubject;
import com.hong.eduservice.entity.ExcelSubjectData;
import com.hong.eduservice.entity.vo.EduSubjectNestedVo;
import com.hong.eduservice.entity.vo.EduSubjectVo;
import com.hong.eduservice.mapper.EduSubjectMapper;
import com.hong.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hong.servicebase.exception.HongEduException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author hong
 * @since 2020-08-05
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void importSubjectData(MultipartFile file, EduSubjectService eduSubjectService) {
        try {
            InputStream is = file.getInputStream();
            EasyExcel.read(is, ExcelSubjectData.class, new SubjectExcelListener(eduSubjectService))
                    .sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
            throw new HongEduException(ResultCode.ERROR, "添加课程分类失败");
        }
    }

    @Override
    public List<EduSubjectNestedVo> nestedList() {
        ArrayList<EduSubjectNestedVo> subjectNestedVoArrayList = new ArrayList<>();

        //一层
        QueryWrapper<EduSubject> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("parent_id", "0");
        wrapper1.orderByAsc("sort","id");
        List<EduSubject> subjects = baseMapper.selectList(wrapper1);
        //二层
        QueryWrapper<EduSubject> wrapper2 = new QueryWrapper<>();
        wrapper2.ne("parent_id", "0");
        wrapper2.orderByAsc("sort","id");
        List<EduSubject> subSubjects = baseMapper.selectList(wrapper2);

        for(int i=0;i<subjects.size();i++){
            EduSubject subject = subjects.get(i);
            EduSubjectNestedVo eduSubjectNestedVo = new EduSubjectNestedVo();
            BeanUtils.copyProperties(subject,eduSubjectNestedVo);
            ArrayList<EduSubjectVo> subSubjectVos = new ArrayList<>();
            for(int j=0;j<subSubjects.size();j++){
                EduSubject subSubject = subSubjects.get(j);
                if(subSubject.getParentId().equals(subject.getId())){
                    EduSubjectVo subSubjectVo = new EduSubjectVo();
                    BeanUtils.copyProperties(subSubject,subSubjectVo);
                    subSubjectVos.add(subSubjectVo);
                }
             }
            eduSubjectNestedVo.setChildren(subSubjectVos);
            subjectNestedVoArrayList.add(eduSubjectNestedVo);
        }
        return subjectNestedVoArrayList;
    }
}
