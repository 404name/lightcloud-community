package com.juelunn.lightcloud.module.system.controller;

import com.alibaba.fastjson.JSON;
import com.juelunn.common.annotation.OptLog;
import com.juelunn.common.constant.OptTypeConst;
import com.juelunn.common.dto.BlogBackInfoDTO;
import com.juelunn.common.enums.FilePathEnum;
import com.juelunn.common.vo.BlogInfoVO;
import com.juelunn.common.vo.Result;
import com.juelunn.common.vo.VoiceVO;
import com.juelunn.common.vo.WebsiteConfigVO;
import com.juelunn.lightcloud.BlogApplication;
import com.juelunn.lightcloud.module.system.service.BlogInfoService;
import com.juelunn.lightcloud.module.websocket.WebSocketController;
import com.juelunn.oss.context.UploadStrategyContext;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @program: lightcloud_community
 * @description:
 * @author: CTGU_LLZ(404name)
 * @create: 2021-12-20 20:03
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BlogInfoControllerTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private BlogInfoService blogInfoService;
    @Autowired
    private WebSocketController webSocketService;
    @Autowired
    private UploadStrategyContext uploadStrategyContext;
    private final Logger logger = LoggerFactory.getLogger(BlogInfoControllerTest.class);

    /**
     * 查看博客信息
     */
    @org.junit.jupiter.api.Test
    void getBlogHomeInfo() {
        logger.info("1. 首页信息获取测试===============================>");
        System.out.println( Result.ok(blogInfoService.getBlogHomeInfo()));
        logger.info("1. 首页信息测试完毕===============================>");
    }

    @org.junit.jupiter.api.Test
    void getBlogBackInfo() {
        logger.info("2. 后台信息获取测试===============================>");
        System.out.println(Result.ok(blogInfoService.getBlogBackInfo()));
        logger.info("2. 后台信息获取测试===============================>");
    }

    @org.junit.jupiter.api.Test
    void savePhotoAlbumCover() {
        logger.info("3. 保存图片测试===============================>{}",Result.ok());
    }

    @org.junit.jupiter.api.Test
    void updateWebsiteConfig() {
        logger.info("4. 保存配置测试===============================>");
        Result<WebsiteConfigVO> websiteConfig = getWebsiteConfig();
        blogInfoService.updateWebsiteConfig(websiteConfig.getData());
        Result.ok();
    }

    @org.junit.jupiter.api.Test
    Result<WebsiteConfigVO> getWebsiteConfig() {
        return Result.ok(blogInfoService.getWebsiteConfig());
    }

    @org.junit.jupiter.api.Test
    String getAbout() {
        String about = blogInfoService.getAbout();
        logger.info("5. 保存配置测试={}",about);
        return about;
    }

    @org.junit.jupiter.api.Test
    void updateAbout() {
        logger.info("6. 更新配置测试={}");
        BlogInfoVO blogInfoVO = JSON.parseObject(getAbout(), BlogInfoVO.class);
        Result.ok();
    }

    @org.junit.jupiter.api.Test
    void sendVoice() {
        logger.info("7. 上传语言={}");
        Result.ok();
    }

    @org.junit.jupiter.api.Test
    void report() {
        logger.info("8. 更新用户信息={}");
        Result.ok();
    }
}
