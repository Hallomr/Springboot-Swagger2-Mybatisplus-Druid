package com.example.mybatisplus;

import com.example.mybatisplus.common.ResultEnum;
import com.example.mybatisplus.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
class MybatisplusApplicationTests {

    @Test
    public void test(){

         ResultEnum.SUCCESS.get(new User());
    }

}
