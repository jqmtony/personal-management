package cn.xt.pmc.management.controller;

import cn.xt.base.pageable.Pager;
import cn.xt.base.web.lib.controller.AdviceController;
import cn.xt.base.web.lib.controller.BaseController;
import cn.xt.base.web.lib.model.IndexCallback;
import cn.xt.pmc.management.model.*;
import cn.xt.pmc.management.service.BlogService;
import cn.xt.pmc.management.service.BlogTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

/**
 * create by xtao
 * create in 2017/11/5 21:19
 */
@Controller
public class PageController extends BaseController {
    @Resource
    private BlogService blogService;
    @Resource
    private BlogTypeService blogTypeService;

    @RequestMapping("/")
    public String root() {
        return "redirect:/index";
    }

    @RequestMapping("index")
    public String index(Model model, PageParamVo pageParamVo) throws UnsupportedEncodingException {
        setBlogPageParam(model,pageParamVo);
        setBlogTypeMenus(model);
        return "index";
    }

    /**
     * 设置下拉菜单列表
     * @param model
     */
    private void setBlogTypeMenus(Model model){
        List<BlogType> blogTypes = blogTypeService.findBlogTypeLvl();
        AdviceController.INDEX_CALLBACKS.add(new IndexCallback() {
            @Override
            public List getHeaderMenus() {
                return blogTypes;
            }
        });
        model.addAttribute("menus",blogTypes);
    }

    /**
     * 设置博客分页参数
     * @param model
     * @throws UnsupportedEncodingException
     */
    private void setBlogPageParam(Model model, PageParamVo pageParamVo) throws UnsupportedEncodingException {
        BlogVo blogVo = new BlogVo();
        blogVo.setPage(pageParamVo.getBlogPage());
        blogVo.setClassify(pageParamVo.getBlogClassify());
        blogVo.setRow(3);
        Pager<Blog> pager = blogService.findConvertPage(blogVo);
        if(pageParamVo.getBlogClassify()!=null){
            pager.setReqParams("&blogClassify="+pageParamVo.getBlogClassify());
        }
        model.addAttribute("pager", pager);
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
