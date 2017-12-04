package cn.xt.base.web.lib.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * create by xtao
 * create in 2017/11/5 21:19
 */
@Controller
public class LoginController {

//    @RequestMapping(value = "login", method = RequestMethod.GET)
//    public String autoBasicLogin(){
//        return "redirect:/index";
//    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(String username, String password, Model model) {
        String errorMsg = "";
        try {
            if (!StringUtils.hasText(username) || !StringUtils.hasText(password))
                throw new UnknownAccountException();

            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            subject.login(token);
            return "redirect:/index";
        } catch (UnknownAccountException uae) {
            uae.printStackTrace();
            errorMsg = "用户名或密码不正确";
        } catch (IncorrectCredentialsException ice) {
            errorMsg = "用户名或密码不正确";
        } catch (LockedAccountException lae) {
            errorMsg = "该账号已被冻结";
        } catch (ExcessiveAttemptsException eae) {
            errorMsg = "用户名或密码错误次数过多";
        } catch (AuthenticationException ae) {
            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            errorMsg = "用户名或密码不正确";
        } catch (Exception e) {
            errorMsg = e.getMessage();
        }
        model.addAttribute("errorMsg", errorMsg);
        return "login";
    }
    /*public String login(HttpServletRequest request,String username , String password){
        System.out.println(username);
        System.out.println(password);
        if("admin".equals(username)&&"admin123".equals(password)){
            request.getSession().setAttribute("currentUser","login");
            return "redirect:/index";
        }
        return "redirect:error/500";
    }*/
}
