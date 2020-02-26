package com.example.mybatisplus.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadZipService {
    String uploadZipFilesAndParse(MultipartFile file) throws Exception;
}
