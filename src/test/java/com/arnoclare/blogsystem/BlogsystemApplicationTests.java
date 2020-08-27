package com.arnoclare.blogsystem;

import com.arnoclare.blogsystem.utils.MailUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogsystemApplicationTests {

    @Autowired
    MailUtils mailUtils;

    @Test
    void contextLoads() {
        mailUtils.sendSimpleEmail("1328504141@qq.com","密码已经重置了","您登录的密码已经初始化为123456");

    }

}
