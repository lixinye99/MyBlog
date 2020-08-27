package com.arnoclare.blogsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableScheduling
@EnableTransactionManagement
@SpringBootApplication
public class BlogsystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogsystemApplication.class, args);
    }

}
