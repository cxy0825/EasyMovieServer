package com.cxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class mongoApplication {
    public static void main(String[] args) {
        SpringApplication.run(mongoApplication.class, args);
    }
}