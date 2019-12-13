package com.example.mybatisplus.vo.req;

import io.swagger.annotations.ApiModelProperty;

public class PageReq {
    @ApiModelProperty(value = "当前页",example = "1",required = true)
    private Integer pageNum;
    @ApiModelProperty(value = "每页记录数",example = "10",required = true)
    private Integer pageSize;

    public Integer getPageNum() {
        if(pageNum<1){
            this.pageNum=1;
        }
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        if(pageNum==null){
            this.pageNum = 1;
        }else {
            this.pageNum = pageNum;
        }
    }

    public Integer getPageSize() {
        if(pageSize<1){
            this.pageSize=10;
        }
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        if(pageSize==null){
            this.pageSize = 10;
        }else {
            this.pageSize = pageSize;
        }
    }
}
