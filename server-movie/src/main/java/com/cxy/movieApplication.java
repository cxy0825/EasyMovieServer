package com.cxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class movieApplication {
    public static void main(String[] args) {

        SpringApplication.run(movieApplication.class, args);
    }
}