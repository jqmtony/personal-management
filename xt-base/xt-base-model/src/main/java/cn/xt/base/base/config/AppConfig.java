package cn.xt.base.base.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan(basePackages = {"cn.xt.**.service",
        "cn.xt.**.daotemp",
        "cn.xt.**.aop",
        "cn.xt.**.properties",
        "cn.xt.**.config"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ImportResource(locations = {"classpath*:spring/spring-*.xml"})
//@ImportResource(locations = {"classpath:spring/spring-es-config.xml",
//        "classpath:spring/spring-dbmessage.xml"})
public class AppConfig {

}