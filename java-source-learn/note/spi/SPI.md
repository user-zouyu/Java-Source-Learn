# SPI 机制

## 什么是SPI机制
> SPI(Service Provider Interface)，是 JDK 内置的一种 **服务提供发现机制**，可以用来启用框架
> 扩展和替换组件， 主要是被框架的开发人员使用，比如 java.sql.Driver 接口，其他不同厂商可以针对同一
> 接口做出不同的实现， MySQL 和 PostgreSQL 都有不同的实现提供给用户，而Java的SPI机制可以为某个接
> 口寻找服务实现。Java中SPI机制主要思想是将装配的控制权移到程序之外，在模块化设计中这个机制尤其重要，
> 其核心思想就是 **解耦**。 
> 
> 调用方提供统一的 **标准服务接口** (Interface) 定义，由服务提供方提供接口实现。 需要在 classpath 下的
> META-INF/services/ 目录里创建一个以服务接口命名的文件，这个文件里的内容就是这个接口的具体的实现类。
> 当其他的程序需要这个服务的时候，就可以通过查找这个jar包（一般都是以jar包做依赖）的META-INF/services/
> 中的配置文件，配置文件中有接口的具体实现类名，可以根据这个类名进行加载实例化，就可以使用该服务了。
> JDK中查找服务的实现的工具类是：java.util.ServiceLoader。

## 简单使用
### 提供接口
```java
// class name: com.zouyu.hello.Hello
public interface Hello {
    void hello();
}
```

### 接口实现

```java
// class name: com.zouyu.hello.impl.HelloImpl
public class HelloImpl implements Hello {
    public HelloImpl() {
        System.out.println("Hello Impl Constructor");
    }

    @Override
    public void hello() {
        System.out.println("Hello Impl");
    }
}
```

### 配置 

在 classpath 下创建文件夹 META-INF/services/ ，下该文件夹下创建文件 (com.zouyu.hello.Hello) 文件名为接口的全类名，没有后缀。文件里写入实现类的全类名。如有多个实现类，换行写入

文件里的内容如下：

```
com.zouyu.hello.impl.Hello
```

### 使用

```java
public class Main {
    public static void main(String[] args) {
        ServiceLoader<Hello> load = ServiceLoader.load(Hello.class);

        for (Hello next : load) {
            next.hello();
        }
    }

}
```

## 实现原理
...