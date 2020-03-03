package com.example.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mybatisplus.entity.User;
import com.example.mybatisplus.entity.UserEntity;
import com.example.mybatisplus.mapper.UserMapper;
import com.example.mybatisplus.service.UserService;
import com.example.mybatisplus.util.LoadCache;
import com.example.mybatisplus.vo.req.UserReq;
import com.example.mybatisplus.vo.resp.PageResp;
import com.example.mybatisplus.vo.resp.UserResp;
import org.ehcache.shadow.org.terracotta.statistics.Statistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.xml.validation.Validator;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author generator
 * @since 2019-11-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private javax.validation.Validator validator =  Validation.buildDefaultValidatorFactory().getValidator();
    //校验异常数据
    private static List<UserEntity> userEntities = new ArrayList<>();

    @Autowired
    private UserMapper userMapper;

    @Override
    public PageResp<UserResp> getUserList(UserReq userReq) {
        Page page = new Page(userReq.getPageReq().getPageNum(), userReq.getPageReq().getPageSize());
        Page<UserResp> userRespPage = userMapper.getUserList(page, userReq);
        PageResp<UserResp> pageResp = new PageResp();
        if (userRespPage != null) {
            pageResp.setTotalPage(userRespPage.getPages());
            pageResp.setTotalSize(userRespPage.getTotal());
            if (userRespPage.getRecords() != null) {
                pageResp.setRecords(userRespPage.getRecords());
            }
        }
        return pageResp;
    }

    @Override
    public boolean save(List<Object> list,boolean isFinish) {
        //导入前对数据去重
        List<UserEntity> users = list.stream().map(u -> {
            return (UserEntity) u;
        }).collect(Collectors.toList());

        //入库数据
        List<UserEntity> temp = new ArrayList<>();

        //校验
        for (UserEntity user : users) {
            Set<ConstraintViolation<UserEntity>> validate = validator.validate(user);
            if(!validate.isEmpty()){
                for (ConstraintViolation<UserEntity> v : validate) {
                    String message = v.getMessage();
                    System.out.println(message);
                }
                userEntities.add(user);
            }else{
                temp.add(user);
            }
        }

        //解析excel完毕,异常数据放入内存
        if(isFinish){
            LoadCache.putLoadingCache("errorData",userEntities);
            userEntities = null;
        }

        //对excel中username重复数据去重
        temp = temp.stream()
                .collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(UserEntity::getUsername))), ArrayList::new));
        //对数据库存在数据去重
        temp.removeIf(s -> {
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<User>().eq("username",s.getUsername());
            if(userMapper.selectList(userQueryWrapper).size()>0){
                return true;
            }
            return false;
        });
        //userMapper.insertBatch(users);
        if(temp.size()>0) {
            userMapper.insertBatch(temp);
        }
        return true;
    }
}
