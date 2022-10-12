package com.zouyu.base.spi;

import java.util.ServiceLoader;

/**
 * @author ZouYu 2022/10/6 16:09
 * @version 1.0.0
 */
public class Main {
    public static void main(String[] args) {
        ServiceLoader<Hello> load = ServiceLoader.load(Hello.class);

        for (Hello next : load) {
            next.hello();
        }
    }

}
