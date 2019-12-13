package com.example.mybatisplus.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author generator
 * @since 2019-11-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="User对象", description="")
@TableName(value = "users")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    @ExcelProperty("主键id")
    private Integer id;

    @ApiModelProperty(value = "用户密码")
    @ExcelProperty("用户密码")
    private String password;

    @ApiModelProperty(value = "用户名")
    @ExcelProperty("用户名")
    private String username;

    @ApiModelProperty(value = "状态 1启用 0 停用")
    @ExcelProperty("用户状态")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @ExcelProperty("创建时间")
    @DateTimeFormat(value = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @ExcelProperty("更新时间")
    @DateTimeFormat(value = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty(value = "相关内容")
    @ExcelProperty("相关内容")
    @TableField(el = "content,typeHandler=com.example.mybatisplus.common.handler.JsonTypeHandler")
    private Content content;
}
