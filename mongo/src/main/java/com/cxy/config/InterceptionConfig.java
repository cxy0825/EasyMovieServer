package com.cxy.config;

import com.cxy.interception.VerifyInterception;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class InterceptionConfig implements WebMvcConfigurer {
    @Bean
    VerifyInterception verifyInterception() {
        return new VerifyInterception();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(verifyInterception())
                .addPathPatterns("/**")
                .excludePathPatterns("/**/login/**");
    }
}
