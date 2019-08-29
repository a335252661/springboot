package com.example.springboot;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author by cld
 * @date 2019/8/29  9:45
 * @description:
 */
@Configuration
//@EnableWebMvc
public class WebMvcConfigurerConfig implements WebMvcConfigurer {
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");

        return corsConfiguration;
    }

//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", buildConfig());
//        return new CorsFilter(source);
//    }
//
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //静态资源文件访问
        registry.addResourceHandler("/**").addResourceLocations(
                "classpath:/static/");
        //swagger-ui.html 直接访问，不做映射
        registry.addResourceHandler("swagger-ui.html").addResourceLocations(
                "classpath:/META-INF/resources/");

        //xxx.html 网页直接访问
        registry.addResourceHandler("xxx.html").addResourceLocations(
                "classpath:/static/");

        registry.addResourceHandler("/webjars/**").addResourceLocations(
                "classpath:/META-INF/resources/webjars/");
    }
}
