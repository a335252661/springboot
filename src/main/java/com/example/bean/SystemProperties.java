package com.example.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author by cld
 * @date 2019/8/12  11:29
 * @description:
 */
@Component //表明当前类是一个 Java Bean
@ConfigurationProperties(prefix = "system") //表示获取前缀为 system 的配置信息
public class SystemProperties {

    private String name;

    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
