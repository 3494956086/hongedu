package com.hong.eduservice.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class EduSubjectNestedVo implements Serializable {
    private String id;
    private String title;
    private List<EduSubjectVo> children = new ArrayList<>();
}
