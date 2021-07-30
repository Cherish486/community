package com.nowcoder.community.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class MailClient {

    private static final Logger logger = LoggerFactory.getLogger(MailClient.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from; // 发邮件的邮箱

    public void sendMail(String to, String subject, String context){
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom(from);  // 设置发送邮箱
            helper.setTo(to);      // 设置接收邮箱
            helper.setSubject(subject); // 设置标题
            helper.setText(context,true); // 设置邮件内容，true表示可以识别html文本

            mailSender.send(helper.getMimeMessage());
        } catch (MessagingException e) {
            logger.error("发送文件失败："+e.getMessage());
        }
    }
}
