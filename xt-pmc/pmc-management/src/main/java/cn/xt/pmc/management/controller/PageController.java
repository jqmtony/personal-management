package cn.xt.pmc.management.controller;

import cn.xt.base.pageable.Pager;
import cn.xt.base.web.lib.controller.BaseController;
import cn.xt.pmc.management.model.Blog;
import cn.xt.pmc.management.model.BlogVo;
import cn.xt.pmc.management.service.BlogService;
import org.apache.shiro.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * create by xtao
 * create in 2017/11/5 21:19
 */
@Controller
public class PageController extends BaseController {
    @Resource
    private BlogService blogService;

    @RequestMapping("/")
    public String root() {
        return "redirect:/index";
    }

    @RequestMapping("index")
    public String index(Model model, HttpServletRequest req) throws UnsupportedEncodingException {
        BlogVo blogVo = new BlogVo();
        String blogPageText = req.getParameter("blogPage");
        if(StringUtils.hasText(blogPageText)){
            blogVo.setBlogPage(Integer.parseInt(blogPageText));
        }
        Pager<Blog> pager = blogService.findConvertPage(blogVo);
        model.addAttribute("pager", pager);
        return "index";
    }

    @RequestMapping("login")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("unauthorized")
    public String unauthorized(){
        return "unauthorized";
    }
}
