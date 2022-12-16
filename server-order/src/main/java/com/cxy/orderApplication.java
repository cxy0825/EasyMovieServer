package com.cxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class orderApplication {
    public static void main(String[] args) {
        SpringApplication.run(orderApplication.class, args);

    }
}