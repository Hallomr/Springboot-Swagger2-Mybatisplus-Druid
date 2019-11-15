package com.example.mybatisplus.service;

import com.example.mybatisplus.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mybatisplus.vo.req.UserReq;
import com.example.mybatisplus.vo.resp.PageResp;
import com.example.mybatisplus.vo.resp.UserResp;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author generator
 * @since 2019-11-14
 */
public interface UserService extends IService<User> {

    PageResp<UserResp> getUserList(UserReq userReq);
}
