package com.example.mybatisplus.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(value = "user请求实体")
public class UserReq {
    @ApiModelProperty(value = "状态 1启用 0 停用")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "分页条件",required = true)
    private PageReq pageReq;
}
