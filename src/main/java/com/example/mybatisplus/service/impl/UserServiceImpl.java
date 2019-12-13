package com.example.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.entity.Content;
import com.example.mybatisplus.entity.User;
import com.example.mybatisplus.entity.UserEntity;
import com.example.mybatisplus.mapper.UserMapper;
import com.example.mybatisplus.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mybatisplus.vo.req.UserReq;
import com.example.mybatisplus.vo.resp.PageResp;
import com.example.mybatisplus.vo.resp.UserResp;
import org.apache.velocity.runtime.directive.Foreach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author generator
 * @since 2019-11-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public PageResp<UserResp> getUserList(UserReq userReq) {
        Page page = new Page(userReq.getPageReq().getPageNum(),userReq.getPageReq().getPageSize());
        Page<UserResp> userRespPage = userMapper.getUserList(page,userReq);
        PageResp<UserResp> pageResp = new PageResp();
        if(userRespPage!=null){
            pageResp.setTotalPage(userRespPage.getPages());
            pageResp.setTotalSize(userRespPage.getTotal());
            if(userRespPage.getRecords()!=null){
                pageResp.setRecords(userRespPage.getRecords());
            }
        }
        return pageResp;
    }

    @Override
    public boolean save(List<Object> list) {
       List<UserEntity> users = list.stream().map(u->{return (UserEntity)u;}).collect(Collectors.toList());
        userMapper.insertBatch(users);
        return true;
    }
}
