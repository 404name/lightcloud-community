package com.juelunn.common.consumer;

import com.alibaba.fastjson.JSON;
import com.juelunn.common.constant.MQPrefixConst;
import com.juelunn.common.dto.EmailDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * 通知邮箱
 *
 * @author CTGU_LLZ(404name)
 * @date 2021/06/13
 * @since 1.0.0
 **/
@Component
@RabbitListener(queues = MQPrefixConst.EMAIL_QUEUE)
public class EmailConsumer {
    private static final Logger logger = LoggerFactory.getLogger(EmailConsumer.class);
    /**
     * 邮箱号
     */
    @Value("${spring.mail.username}")
    private String email;

    @Autowired
    private JavaMailSender javaMailSender;

    @RabbitHandler
    public void process(byte[] data) throws InterruptedException {
        try{
            EmailDTO emailDTO = JSON.parseObject(new String(data), EmailDTO.class);
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setFrom(email);
            mimeMessageHelper.setTo(emailDTO.getEmail());
            mimeMessageHelper.setSubject("【轻云社区】" + emailDTO.getSubject());
            mimeMessageHelper.setText(emailDTO.getContent(), false);
            Thread.sleep(1000);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            logger.info("============邮箱冲突===========");
            e.printStackTrace();
        } catch (Exception e){
            Thread.sleep(10000);
            e.printStackTrace();
        }

    }

}
