package com.cxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class authorizationApplication {
    public static void main(String[] args) {

        SpringApplication.run(authorizationApplication.class, args);
    }
}