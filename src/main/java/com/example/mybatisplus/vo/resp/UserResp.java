package com.example.mybatisplus.vo.resp;

import com.example.mybatisplus.common.enums.IEnum;
import com.example.mybatisplus.common.enums.StatusEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(value = "user响应实体类")
@JsonInclude(JsonInclude.Include.ALWAYS)
public class UserResp {
    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "用户密码")
    private String password;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "状态 1启用 0 停用")
    @IEnum(StatusEnum.class)
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
