package com.example.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 程刘德
 * @version 1.0
 * @Description TODO
 * @date 2019/10/10
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {


    /**
     * web拦截器
     * @return
     */
    public WebInterceptor webInterceptor(){
        WebInterceptor webInterceptor=new WebInterceptor();
        return webInterceptor;
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 让配置的web拦截器生效
        registry.addInterceptor(webInterceptor());
    }
}
