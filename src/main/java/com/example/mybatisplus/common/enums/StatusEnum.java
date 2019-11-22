package com.example.mybatisplus.common.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public enum StatusEnum implements BaseEnum {
    USING(0,"启用"),
    FORBIDDEN(1,"禁用");
    private Integer code;
    private String value;

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
