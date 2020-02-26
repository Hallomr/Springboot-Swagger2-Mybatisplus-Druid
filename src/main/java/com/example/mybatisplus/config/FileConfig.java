package com.example.mybatisplus.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "upload")
@Component
@Order
public class FileConfig {
    public static String localtion;
    public static String maxFileSize;
    public static String maxRequestSize;

    /**
     * @param localtion the localtion to set
     */
    public void setLocaltion(String localtion) {
        FileConfig.localtion = localtion;
    }

    /**
     * @param maxFileSize the maxFileSize to set
     */
    public void setMaxFileSize(String maxFileSize) {
        FileConfig.maxFileSize = maxFileSize;
    }

    /**
     * @param maxRequestSize the maxRequestSize to set
     */
    public void setMaxRequestSize(String maxRequestSize) {
        FileConfig.maxRequestSize = maxRequestSize;
    }
}
