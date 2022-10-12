package com.zouyu.classloader;

import com.zouyu.base.spi.Hello;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author ZouYu 2022/10/7 19:55
 * @version 1.0.0
 */
public class Demo2 {
    @SuppressWarnings("all")
    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        File file = new File("C:\\Users\\zouyu\\Desktop\\home");
        URL url = file.toURI().toURL();

        URLClassLoader classLoader = new URLClassLoader(new URL[]{url});
        Class<Hello> hello = (Class<Hello>) classLoader.loadClass("com.zouyu.spi.HelloLoaderImpl");
        Hello o = (Hello)hello.newInstance();
        o.hello();
    }

}
