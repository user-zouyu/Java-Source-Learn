package com.zouyu.base.spi.impl;

import com.zouyu.base.spi.Hello;

/**
 * @author ZouYu 2022/10/6 16:08
 * @version 1.0.0
 */
public class HelloImpl implements Hello {
    public HelloImpl() {
        System.out.println("Hello Impl Constructor");
    }

    @Override
    public void hello() {
        System.out.println("Hello Impl");
    }
}
