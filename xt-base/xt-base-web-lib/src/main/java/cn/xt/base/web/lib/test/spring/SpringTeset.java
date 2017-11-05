package cn.xt.base.web.lib.test.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTeset {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext();
        System.out.println(ctx);
    }
}
