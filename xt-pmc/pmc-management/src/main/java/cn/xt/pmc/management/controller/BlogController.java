package cn.xt.pmc.management.controller;

import cn.xt.pmc.management.model.Blog;
import cn.xt.pmc.management.model.ContentType;
import cn.xt.pmc.management.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;

/**
 * create by xtao
 * create in 2017/11/19 0:10
 */
@RequestMapping("blog")
@Controller
public class BlogController {

    @Resource
    private BlogService blogService;

    @RequestMapping(value = "blogging",method = RequestMethod.GET)
    public String blogging() {
        return "blog/blogging";
    }

    @RequestMapping(value = "blogging",method = RequestMethod.POST)
    public String blogging(Blog blog) throws Exception {
        System.out.println(blog);
        blog.setCreateTime(new Date());
        blog.setTitle("一篇测试博客");
        blog.setContentType(ContentType.markdown);
        blogService.insert(blog);
        return "redirect:/index";
    }
}
