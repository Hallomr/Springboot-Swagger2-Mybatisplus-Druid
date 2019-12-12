package com.example.mybatisplus.vo.resp;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DateResp {
    private String date;
    private Integer number;
}
