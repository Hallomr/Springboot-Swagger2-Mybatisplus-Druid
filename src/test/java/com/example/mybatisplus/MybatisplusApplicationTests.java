package com.example.mybatisplus;

import com.example.mybatisplus.common.ResultEnum;
import com.example.mybatisplus.entity.User;
import com.example.mybatisplus.service.UserService;
import com.example.mybatisplus.vo.resp.DateResp;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM.dd");
        String format = now.format(dateTimeFormatter);
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

    @Test
    public void DateRespTest(){
        ArrayList<DateResp> objects = new ArrayList<>();
        LocalDate now = LocalDate.now();
        //最近三个月
        DateTimeFormatter three = DateTimeFormatter.ofPattern("MM月");
        //String format = now.format(three);
        for(int i=2;i>=0;i--){
            LocalDate localDate = now.plusMonths(-i);
            String format = localDate.format(three);
            objects.add(new DateResp().setDate(format).setNumber(0));
        }
        System.out.println(objects);

        ArrayList<DateResp> fourList = new ArrayList<>();
        //最近四周
        DateTimeFormatter four = DateTimeFormatter.ofPattern("MM.dd");
        for(int i=3;i>=0;i--){
            String sunday = now.plusWeeks(-i).with(DayOfWeek.SUNDAY).format(four);
            String monday = now.plusWeeks(-i).with(DayOfWeek.MONDAY).format(four);
            fourList.add(new DateResp().setDate(monday+"-"+sunday).setNumber(0));
        }
        System.out.println(fourList);

        //最近七日
        ArrayList<DateResp> sevenList = new ArrayList<>();
        DateTimeFormatter seven = DateTimeFormatter.ofPattern("MM.dd");
        for(int i=6;i>=0;i--){
            LocalDate localDate = LocalDate.now().plusDays(-i);
            sevenList.add(new DateResp().setDate(localDate.format(seven)).setNumber(0));
        }
        System.out.println(sevenList);
        //24时
        ArrayList<DateResp> oneDay = new ArrayList<>();
        for(int i=0;i<=23;i++){
            oneDay.add(new DateResp().setDate(i+"时").setNumber(0));
        }
        System.out.println(oneDay);
    }

    @Test
    public void instance(){
        Object user = new User();
        if(user instanceof UserService){
            System.out.println("test.......");
        }

    }
}
