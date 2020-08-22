package com.hong.eduservice.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ExcelSubjectData implements Serializable {
    @ExcelProperty(index=0)
    private String oneSubjectName;
    @ExcelProperty(index=1)
    private String twoSubjectName;
}
