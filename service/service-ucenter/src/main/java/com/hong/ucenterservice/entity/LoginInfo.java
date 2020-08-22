package com.hong.ucenterservice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value="登录对象", description="登录对象")
public class LoginInfo {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.INPUT)
    private String id;
    private Integer age;
    private String avatar;
    private String mobile;
    private String  nickname;
    private Integer sex;
}
