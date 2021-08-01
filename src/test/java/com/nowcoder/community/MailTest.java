package com.nowcoder.community;

import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.UserService;
import com.nowcoder.community.utils.CommunityUtil;
import com.nowcoder.community.utils.MailClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;

@ContextConfiguration(classes = CommunityApplication.class)
@SpringBootTest
public class MailTest {

    @Autowired
    private MailClient mailClient;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void testTextMail() {
        mailClient.sendMail("2277801389@qq.com","TEST","Welcome!");
    }

    @Test
    public void testHtmlMail(){
        Context context = new Context();
        context.setVariable("username","sunday");

        String content = templateEngine.process("/mail/demo", context);
        System.out.println(content);

        mailClient.sendMail("2277801389@qq.com","HTML",content);
    }

    @Test
    public void testSendVerifyCode() {
        User user = userMapper.selectByEmail("2277801389@qq.com");

        Context context = new Context();
        context.setVariable("email",user.getEmail());
        String code = CommunityUtil.generateUUID().substring(0,5);
        context.setVariable("code",code);

        String content = templateEngine.process("/mail/forget",context);
        mailClient.sendMail("2277801389@qq.com","找回密码",content);
    }
}
