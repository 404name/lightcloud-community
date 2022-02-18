//package com.juelunn.lightcloud.module.blog.contorller;
//
//import com.juelunn.common.vo.Result;
//import com.juelunn.lightcloud.module.blog.service.ArticleService;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import static org.junit.jupiter.api.Assertions.*;
//
///**
// * @program: lightcloud_community
// * @description:
// * @author: CTGU_LLZ(404name)
// * @create: 2021-12-20 20:47
// **/
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
////继承AbstractTransactionalJUnit4SpringContextTests
////保证测试中的事务回滚，不影响数据库
//class ArticleControllerTest extends AbstractTransactionalJUnit4SpringContextTests {
//    @Autowired
//    private ArticleService articleService;
//    @Test
//    void listArticles() {
//        System.out.println(Result.ok(articleService.listArchives()));
//    }
//}
