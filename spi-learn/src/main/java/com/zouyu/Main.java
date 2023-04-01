package com.zouyu;

import java.util.ServiceLoader;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        ServiceLoader<Hello> hellos = ServiceLoader.load(Hello.class);
        for (Hello hello : hellos) {
            hello.hello();
        }
    }
}