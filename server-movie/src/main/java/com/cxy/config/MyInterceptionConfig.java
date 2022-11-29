package com.cxy.config;

import com.cxy.intercepter.Verify;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyInterceptionConfig implements WebMvcConfigurer {
    @Bean
    public Verify verify() {
        return new Verify();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(verify())
                .addPathPatterns("/movie/**")
                .excludePathPatterns("/movie/public/**");
    }
}
