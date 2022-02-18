package com.juelunn.oss.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * description: minio配置类
 *
 * @author: weirx
 * @time: 2021/8/25 9:47
 */
@Data
@Component
@ConfigurationProperties(prefix = "upload.minio")
public class
MinioConfigProperties {

    /**
     * 端点
     */
    private String endpoint;
    /**
     * 用户名
     */
    private String accessKey;
    /**
     * 密码
     */
    private String secretKey;

    /**
     * 桶名称
     */
    private String bucketName;
}
