package cn.xt.pmc.management.service;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * create by xtao
 * create in 2017/12/4 0:37
 */
//@Component
public class BlogListJob implements ApplicationListener<ContextRefreshedEvent> {
//    @Resource
//    private BlogService blogService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
    }
}
