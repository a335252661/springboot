package com.example.test;


import org.apache.poi.ss.formula.functions.T;

/**
 * @author 程刘德
 * @version 1.0
 * @Description TODO
 * @date 2019/10/10
 */
public class test1 {
    public static void main(String[] args) {
        test1 test = new test1();
        test.fun(test1.class);
    }


    public <T> T fun2(T t) {
        return t;
    }

    public <T> T fun(Class<T> cla) {
        T mm = null;
        return mm;
    }
}
