package com.juelunn.common.consumer;

import com.alibaba.fastjson.JSON;
import com.juelunn.common.constant.MQPrefixConst;
import com.juelunn.common.entity.Article;
import com.juelunn.common.util.BeanCopyUtils;
import com.juelunn.lightcloud.module.blog.dao.ElasticsearchDao;
import com.juelunn.common.dto.ArticleSearchDTO;
import com.juelunn.common.dto.MaxwellDataDTO;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * maxwell监听数据
 *
 * @author CTGU_LLZ(404name)
 * @date 2021/08/02
 */
@Component
@RabbitListener(queues = MQPrefixConst.MAXWELL_QUEUE)
public class MaxWellConsumer {
    @Autowired
    private ElasticsearchDao elasticsearchDao;

    @RabbitHandler
    public void process(byte[] data) {
        // 获取监听信息
        MaxwellDataDTO maxwellDataDTO = JSON.parseObject(new String(data), MaxwellDataDTO.class);
        // 获取文章数据
        Article article = JSON.parseObject(JSON.toJSONString(maxwellDataDTO.getData()), Article.class);
        // 判断操作类型
        System.out.println("获取rabbimq消息");
        switch (maxwellDataDTO.getType()) {
            case "insert":
            case "update":
                // 更新es文章
                elasticsearchDao.save(BeanCopyUtils.copyObject(article, ArticleSearchDTO.class));
                break;
            case "delete":
                // 删除文章
                elasticsearchDao.deleteById(article.getId());
                break;
            default:
                break;
        }
    }

}
