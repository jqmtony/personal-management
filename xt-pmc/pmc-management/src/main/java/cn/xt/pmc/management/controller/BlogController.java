package cn.xt.pmc.management.controller;

import cn.xt.base.model.Constant;
import cn.xt.base.util.HtmlUtil;
import cn.xt.base.web.lib.controller.BaseController;
import cn.xt.pmc.management.exceptions.BlogNoPermissionException;
import cn.xt.pmc.management.exceptions.BlogRepeatException;
import cn.xt.pmc.management.model.Blog;
import cn.xt.pmc.management.model.BlogState;
import cn.xt.pmc.management.model.ContentType;
import cn.xt.pmc.management.service.BlogService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.io.IOException;
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
    protected Logger logger = LoggerFactory.getLogger(BlogController.class);

    @Resource
    private BlogService blogService;

    @RequestMapping(value = "blogging", method = RequestMethod.GET)
    public String blogging(@RequestParam(required = false) Long id, Model model) throws UnsupportedEncodingException {
        if(getPrincipalId()==null){
            return "redirect:/login";
        }
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

    @RequiresAuthentication
    @RequestMapping(value = "blogging", method = RequestMethod.POST)
    public String blogging(Blog blog,Model model) throws IOException {
        if(!StringUtils.hasText(blog.getTitle())
                || !StringUtils.hasText(blog.getHtml())
                || !StringUtils.hasText(blog.getOriginal())){
            sendErrorMsg(model,"请写好在提交吧！",blog);
            return "blog/blogging";
        }
        //编码内容
        initBlogInfo(blog);

        if(blog.getId()==null){
            try {
                blogService.insertEntity(blog);
            } catch (BlogRepeatException e) {
                sendErrorMsg(model,"该标题已存在，请重新命名！",blog);
                return "blog/blogging";
            }
        } else{
            try {
                blogService.updateEntity(blog);
            } catch (BlogNoPermissionException e) {
                sendErrorMsg(model,"您不是博客创建者，不能修改博客！",blog);
                return "blog/blogging";
            }
        }
        return "redirect:/index";
    }

    @RequestMapping(value = "bloggingDetails", method = RequestMethod.GET)
    public String bloggingDetails(Long id, Model model) throws UnsupportedEncodingException {
        Blog blog = blogService.get(id);
        if(blog==null || !blog.getState().equals(BlogState.normal)){
            return "redirect:/index";
        }
        blog.setOriginal(blog.decode(blog.getOriginal()));
        blog.setHtml(blog.decode(blog.getHtml()));
        model.addAttribute("blog",blog);
        model.addAttribute("canEdit",blog.getCreateBy().equals(getPrincipalId()));
        //发送Meta(关键字,描述)
        sendMeta(model,blog);
        return "blog/bloggingDetals";
    }

    private void initBlogInfo(Blog blog){
        try {
            blog.setContentType(ContentType.markdown);
            String text = HtmlUtil.delHTMLTag(blog.decode(blog.getHtml()));
            blog.setText(URLEncoder.encode(text,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error("初始化博客信息失败：转码失败",e);
        }
    }

    private void sendMeta(Model model,Blog blog) throws UnsupportedEncodingException {
        if(StringUtils.hasText(blog.getTitle())){
            model.addAttribute("keywords",blog.getTitle());
        }
        if(StringUtils.hasText(blog.getText())){
            String desc = URLDecoder.decode(blog.getText(), Constant.UTF8);
            desc = desc.replace("\n", " ");
            desc = desc.substring(0,Math.min(desc.length(),100) );
            model.addAttribute("description",desc);
        }
    }

    private void sendErrorMsg(Model model,String message,Blog blog) {
        try {
            model.addAttribute("blog",blog);
            model.addAttribute("decodeOrginal",blog.decode(blog.getOriginal()));
            model.addAttribute("decodeHtml",blog.decode(blog.getHtml()));
            model.addAttribute("errorMsg",message);
        } catch (UnsupportedEncodingException e) {
            logger.error("设置博客操作错误信息失败：解码失败",e);
        }
    }
}
