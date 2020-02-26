package com.example.mybatisplus.controller;

import com.example.mybatisplus.service.UploadZipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
@RestController
@RequestMapping("/zip")
public class UploadZipController {
    @Autowired
    private UploadZipService uploadZipService;
    /**
     * 这个deomo入参的类型是MultipartFile，很多网上的例子是File类型
     * @param file (zip)
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/addPersonsFileOfZip")
    public String addPersonsFileOfZip(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception {
        //String createdId = request.getParameter(KEY_CREATED_ID);
        //正常上这里需要检查一下createdId是否为空

        //原则上这个uploadZipFilesAndParse方法需要写到service和serviceImpl中
        String result =uploadZipService.uploadZipFilesAndParse(file);
        return  result;
    }
}
