package com.juelunn.lightcloud;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;


/**
 * TODO:
 * 接入支付系統
 * 用户通过博客打赏的钱，通过rabbitMQ会回传到该卡片作者账号下
 * 线下群进行红包提现
 */


/**
 * 博客启动类
 *
 * @author CTGU_LLZ(404name)
 * @date 2021/08/14
 */
@MapperScan({"com.juelunn.lightcloud.module.blog.dao",
        "com.juelunn.lightcloud.module.system.dao",
        "com.juelunn.lightcloud.module.data.dao",
        "com.juelunn.lightcloud.module.shop.dao"})
@SpringBootApplication()
@ComponentScan(basePackages = {"com.juelunn"})
@EnableScheduling
public class BlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
