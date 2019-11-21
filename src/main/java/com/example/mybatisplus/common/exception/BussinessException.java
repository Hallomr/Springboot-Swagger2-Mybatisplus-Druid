package com.example.mybatisplus.common.exception;

import lombok.Data;

/*
* 业务异常
* */
@Data
public class BussinessException extends RuntimeException{
    private String code;
    private String msg;
    public BussinessException(String code,String msg){
        this.code=code;
        this.msg=msg;
    }
}
