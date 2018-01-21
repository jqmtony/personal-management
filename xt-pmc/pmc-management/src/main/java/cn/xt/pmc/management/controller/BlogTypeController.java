package cn.xt.pmc.management.controller;

import cn.xt.pmc.management.model.BlogType;
import cn.xt.pmc.management.service.BlogTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * create by xt
 */
@RequestMapping("blogType")
@Controller
public class BlogTypeController {
    @Resource
    private BlogTypeService blogTypeService;

    @RequestMapping(value = "getRootNodes",method = RequestMethod.POST)
    @ResponseBody
    public List<BlogType> getRootNodes(){
        List<BlogType> list = blogTypeService.findBlogTypeLvl();
        return list;
    }
}
