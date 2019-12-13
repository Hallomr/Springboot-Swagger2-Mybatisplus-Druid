package com.example.mybatisplus.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.example.mybatisplus.common.listener.UploadDataListener;
import com.example.mybatisplus.entity.User;
import com.example.mybatisplus.entity.UserEntity;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

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

    /**
     * 文件下载并且失败的时候返回json（默认失败了会返回一个有部分数据的Excel）
     *
     * @since 2.1.1
     */
    @GetMapping("/download")
    public void download(HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("测试", "UTF-8");
            //String fileName = new String("测试".getBytes(), "ISO-8859-1");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream(), User.class).autoCloseStream(Boolean.FALSE).sheet("模板")
                    .doWrite(userService.list());
        } catch (Exception e) {
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String, String> map = new HashMap<String, String>();
            map.put("status", "failure");
            map.put("message", "下载文件失败" + e.getMessage());
            response.getWriter().println(JSON.toJSONString(map));
        }
    }

    @GetMapping("/downloadModel")
    public void downloadModel(HttpServletResponse response){
        try {
            // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("模板", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), User.class).sheet("模板").doWrite(userService.list());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件上传
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link User}
     * <p>
     * 2. 由于默认异步读取excel，所以需要创建excel一行一行的回调监听器，参照{@link UploadDataListener}
     * <p>
     * 3. 直接读即可
     */
    @PostMapping("upload")
    @ResponseBody
    public String upload(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), UserEntity.class, new UploadDataListener(userService)).sheet().doRead();
        return "success";
    }


}

