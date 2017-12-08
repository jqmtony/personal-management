package cn.xt.pmc.management.controller;

import cn.xt.base.pageable.Pager;
import cn.xt.base.web.lib.controller.BaseController;
import cn.xt.base.web.lib.data.State;
import cn.xt.pmc.management.model.Blog;
import cn.xt.pmc.management.model.BlogState;
import cn.xt.pmc.management.model.BlogVo;
import cn.xt.pmc.management.service.BlogService;
import org.apache.shiro.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyEditorSupport;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
}
