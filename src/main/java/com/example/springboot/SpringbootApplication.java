package com.example.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//是一个组合注解（@Configuration、@EnableAutoConfiguration、@ComponentScan）
//扫描mapper接口
@MapperScan({"com.example.mapperInterface","com.example.mapping"})
@SpringBootApplication(scanBasePackages = {"com.example.*"})
public class SpringbootApplication {

    //用于启动应用程序
    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }

}
