package com.example.mybatisplus.vo.req;

import io.swagger.annotations.ApiModelProperty;

public class PageReq {
    @ApiModelProperty(value = "当前页",example = "1",required = true)
    private Integer pageNum;
    @ApiModelProperty(value = "每页记录数",example = "10",required = true)
    private Integer pageSize;

    public Integer getPageNum() {
        if(pageNum<1){
            pageNum=1;
        }
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        if(pageSize<1){
            pageSize=1;
        }
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
