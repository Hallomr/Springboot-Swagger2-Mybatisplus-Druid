package com.example.mybatisplus.vo.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "分页实体类")
@JsonInclude(JsonInclude.Include.ALWAYS)
public class PageResp<T> {
    @ApiModelProperty(value = "总记录条数")
    private long totalSize;
    @ApiModelProperty(value = "总页数")
    private long totalPage;
    @ApiModelProperty(value = "列表数据")
    private List<T> records;
}
