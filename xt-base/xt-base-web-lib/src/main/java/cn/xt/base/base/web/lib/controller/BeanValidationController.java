package cn.xt.base.base.web.lib.controller;

import cn.xt.base.base.web.lib.data.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import javax.validation.ValidationException;

/**
 * spring 4.0 新特性 BeanValidation
 */
@Controller
@RequestMapping("beanvalid")
public class BeanValidationController {
    //http://localhost/beanvalid/save?username=12
    @RequestMapping("save")
    public String saveUser(@Valid  User user, BindingResult result){
        if(result.hasErrors()){
            result.getFieldErrors().forEach(error ->{
                String fieldName = error.getField();
                String code = error.getCode();
                String message = error.getDefaultMessage();
                System.out.println(fieldName+","+code);
                System.out.println(message);
            });
            throw new ValidationException();
        }
        return "redirect:/index.jsp";
    }
}
