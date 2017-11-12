package cn.xt.pmc.management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * create by xtao
 * create in 2017/11/5 21:19
 */
@Controller
public class LoginController {
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public String login(HttpServletRequest request,String username , String password){
        System.out.println(username);
        System.out.println(password);
        if("admin".equals(username)&&"admin123".equals(password)){
            request.getSession().setAttribute("currentUser","login");
            return "redirect:/index";
        }
        return "redirect:error/500";
    }
}
