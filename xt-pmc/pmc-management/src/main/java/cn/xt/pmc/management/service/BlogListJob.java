package cn.xt.pmc.management.service;

import cn.xt.base.pageable.PageVo;
import cn.xt.base.pageable.Pager;
import cn.xt.base.web.lib.model.PmcBlogCallBack;
import cn.xt.pmc.management.model.Blog;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

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
