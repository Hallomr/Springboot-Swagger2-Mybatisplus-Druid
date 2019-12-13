package com.example.mybatisplus.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "excel数据导入映射实体")
public class UserEntity  {
    /*@ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    @ExcelProperty("主键id")
    private Integer id;*/
    private Integer id;

    @ApiModelProperty(value = "用户密码")
    //@ExcelProperty("用户密码")
    private String password;

    @ApiModelProperty(value = "用户名")
    //@ExcelProperty("用户名")
    private String username;

    @ApiModelProperty(value = "状态 1启用 0 停用")
    //@ExcelProperty("用户状态")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    //@ExcelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    //@ExcelProperty("更新时间")
    private Date updateTime;

    /*@ApiModelProperty(value = "相关内容")
    @ExcelProperty("相关内容")
    @TableField(el = "content,typeHandler=com.example.mybatisplus.common.handler.JsonTypeHandler")
    private Content content;*/
    private Content content;
}
