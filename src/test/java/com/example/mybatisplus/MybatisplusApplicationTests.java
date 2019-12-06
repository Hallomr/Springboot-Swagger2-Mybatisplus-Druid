package com.example.mybatisplus;

import com.example.mybatisplus.common.ResultEnum;
import com.example.mybatisplus.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
class MybatisplusApplicationTests {

    @Test
    public void test(){

         ResultEnum.SUCCESS.get(new User());
    }

    @Test
    public void list(){
        String[] codes = {"001","002","003"};
        List<String> strings = Arrays.asList(codes);
        List strings1 = new ArrayList<>(strings);
        List objects = new ArrayList<>();
        objects.add("yyyy");
        objects.add("HH");
        objects.add("002");
        strings1.addAll(objects);
        strings1.removeAll(objects);
        System.out.println(strings1);

    }

    @Test
    public void date(){
        LocalDate now = LocalDate.now();
        LocalDate with = now.with(TemporalAdjusters.firstDayOfMonth());
        with = with.minusMonths(2);
        System.out.println(with);
    }

    @Test
    public void java8Test(){
        ArrayList<User> users = new ArrayList<>();
        users.add(new User().setUsername("aa").setPassword("11"));
        users.add(new User().setUsername("bb").setPassword("22"));
        users.add(new User().setUsername("cc").setPassword("33"));
        users.add(new User().setUsername("aa").setPassword("66"));
        //在map中对数据处理后返回list
        List<User> collect = users.stream().map(u -> {
            return new User().setUsername(u.getUsername()).setPassword(null);
        }).collect(Collectors.toList());
        //根据对象属性 分组
        Map<String, List<User>> collect1 = users.stream().collect(Collectors.groupingBy(User::getUsername));

        users.forEach(
                f -> {
                    if(f.getUsername()=="aa"){
                        f.setPassword("66");
                    }
                }
        );
        System.out.println(users);
    }
}
