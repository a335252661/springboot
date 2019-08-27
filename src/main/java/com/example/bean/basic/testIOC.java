package com.example.bean.basic;

import org.springframework.beans.factory.BeanNameAware;


public class testIOC implements BeanNameAware {
    private String size;

    @Override
    public String toString() {
        return "testIOC{" +
                "size='" + size + '\'' +
                '}';
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public void setBeanName(String name) {
        name="ss";
    }
}
