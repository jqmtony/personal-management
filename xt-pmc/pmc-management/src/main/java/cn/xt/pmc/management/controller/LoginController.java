package cn.xt.pmc.management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * create by xtao
 * create in 2017/11/5 21:19
 */
@Controller
public class LoginController {
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public String login(String username ,String password){
        System.out.println(username);
        System.out.println(password);
        return "redirect:/index";
    }
}