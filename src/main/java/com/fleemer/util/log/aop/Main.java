package com.fleemer.util.log.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import java.util.Arrays;

@Configuration
@ComponentScan
@EnableAspectJAutoProxy
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        SomeArbitraryClass aClass = (SomeArbitraryClass) context.getBean("someArbitraryClass");
        aClass.closeConnection(new Object(), "n", Arrays.asList(0, 35, 33434, , -34), true);
    }
}
