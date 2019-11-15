package com.example.mybatisplus.controller;


import com.example.mybatisplus.service.UserService;
import com.example.mybatisplus.vo.req.UserReq;
import com.example.mybatisplus.vo.resp.PageResp;
import com.example.mybatisplus.vo.resp.UserResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author generator
 * @since 2019-11-14
 */
@Api(tags = {"用户"})
@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户列表")
    @PostMapping("/list")
    public PageResp<UserResp> userList(@Validated @RequestBody UserReq userReq){
        PageResp<UserResp> userList = userService.getUserList(userReq);
        logger.info("userList -> {}",userList);
        return userList;
    }

}

