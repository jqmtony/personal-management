package cn.xt.pmc.management.controller;

import cn.xt.base.util.HtmlUtil;
import cn.xt.base.web.lib.controller.BaseController;
import cn.xt.pmc.management.model.Blog;
import cn.xt.pmc.management.model.ContentType;
import cn.xt.pmc.management.service.BlogService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.shiro.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
public class BlogController extends BaseController {

    @Resource
    private BlogService blogService;

    @RequestMapping(value = "blogging", method = RequestMethod.GET)
    public String blogging(@RequestParam(required = false) Long id, Model model) throws UnsupportedEncodingException {
        if (id != null) {
            Blog blog = blogService.get(id);
//            blog.setOriginal(blog.decode(blog.getOriginal()));
//            blog.setHtml(blog.decode(blog.getHtml()));
            model.addAttribute("blog",blog);
            model.addAttribute("decodeOrginal",blog.decode(blog.getOriginal()));
            model.addAttribute("decodeHtml",blog.decode(blog.getHtml()));
        }
        return "blog/blogging";
    }

    @RequestMapping(value = "bloggingDetails", method = RequestMethod.GET)
    public String bloggingDetails(Long id, Model model) throws UnsupportedEncodingException {
        Blog blog = blogService.get(id);
        blog.setOriginal(blog.decode(blog.getOriginal()));
        blog.setHtml(blog.decode(blog.getHtml()));
        model.addAttribute("blog",blog);
        return "blog/bloggingDetals";
    }

    @RequestMapping(value = "blogging", method = RequestMethod.POST)
    public String blogging(Blog blog,Model model) throws Exception {
        if(!StringUtils.hasText(blog.getTitle())
                || !StringUtils.hasText(blog.getHtml())
                || !StringUtils.hasText(blog.getOriginal())){
            model.addAttribute("blog",blog);
            model.addAttribute("decodeOrginal",blog.decode(blog.getOriginal()));
            model.addAttribute("decodeHtml",blog.decode(blog.getHtml()));
            model.addAttribute("errorMsg","请写好在提交吧！");
            return "blog/blogging";
        }
        blog.setContentType(ContentType.markdown);
        String text = HtmlUtil.delHTMLTag(blog.decode(blog.getHtml()));
        blog.setText(URLEncoder.encode(text,"UTF-8"));
        if(blog.getId()==null){
            blog.setCreateTime(new Date());
            blog.setCreateBy(getPrincipalId());
            blogService.insert(blog);
        } else{
            blog.setUpdateBy(getPrincipalId());
            blog.setUpdateTime(new Date());
            blogService.update(blog);
        }
        return "redirect:/index";
    }
}
