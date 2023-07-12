package com.example.requestthrottling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class RequestThrottlingApplication {

    public static void main(String[] args) {
        SpringApplication.run(RequestThrottlingApplication.class, args);
    }

}
