package cn.xt.base.web.lib.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("error")
public class ErrorController {
    @RequestMapping("404")
    public String page404(){
        return "error/404";
    }

    @RequestMapping("500")
    public String page500(){
        return "error/500";
    }
}
