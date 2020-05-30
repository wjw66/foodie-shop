package com.wjw.resource;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author : wjwjava01@163.com
 * @date : 22:19 2020/5/30
 * @description :
 *
 * @PropertySource : properties文件地址
 */
@Data
@Component
@ConfigurationProperties(prefix = "file")
@PropertySource("classpath:file-upload-dev.properties")
public class FileUpload {
    private String imageUserFaceLocation;
    private String faceImgServerUrl;
}
