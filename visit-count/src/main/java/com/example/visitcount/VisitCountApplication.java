package com.example.visitcount;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.example.visitcount.dao")
@EnableScheduling
@EnableAspectJAutoProxy
public class VisitCountApplication {

    public static void main(String[] args) {
        SpringApplication.run(VisitCountApplication.class, args);
    }

}
