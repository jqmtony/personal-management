package cn.xt.base.base.web.lib.controller;

import cn.xt.base.base.web.lib.data.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("paramapping")
public class ParamMappingController {

    //通过form表单提交映射list
    @RequestMapping("form-list")
    public void ListMapping(@RequestParam(required = false) User user){
        System.out.println("user:"+user);
    }
}
