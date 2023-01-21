package com.cxy.config;

import com.cxy.interceptor.VerifyAdminInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyInterceptionConfig implements WebMvcConfigurer {
    @Bean
    public VerifyAdminInterceptor VerifyAdminInterceptor() {

        return new VerifyAdminInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(VerifyAdminInterceptor())
                .addPathPatterns("/order/**")
                .excludePathPatterns(new String[]{"/order/public/**", "/order/aliPay/payNotify", "/order/payment/Info/*", "/order/payment/update"});
    }
}
