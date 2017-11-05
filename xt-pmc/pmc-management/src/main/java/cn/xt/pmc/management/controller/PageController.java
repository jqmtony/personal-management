package cn.xt.pmc.management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * create by xtao
 * create in 2017/11/5 21:19
 */
@Controller
public class PageController {
    @RequestMapping("/")
    public String root(){
        return "redirect:/index";
    }
    @RequestMapping("index")
    public String index(){
        return "index";
    }
}
