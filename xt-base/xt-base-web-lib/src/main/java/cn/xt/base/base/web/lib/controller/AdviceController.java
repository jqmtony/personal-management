package cn.xt.base.base.web.lib.controller;

import cn.xt.base.base.web.lib.data.State;
import cn.xt.base.base.web.lib.data.User;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;
import java.util.HashMap;
import java.util.Map;

/**
 *  ControllerAdvice注解应用举例
 */
@ControllerAdvice
@RequestMapping("controlleradvice")
public class AdviceController {

    @RequestMapping("modelattrpage")
    public String modelpage(){
        //这里并没有model.setAttribute或者request.setAttribute来设置user,但是依然可以使用${user}获取
        return "controlleradvice/test";
    }

    //使用实例1：ModelAttribute，使用该注解可以为所有Controller设置通用的model属性
    @ModelAttribute("modelData")
    public Map<String,Object> setModelAttr(){
        Map<String,Object> modelData = new HashMap<String,Object>();
        modelData.put("username","aaa");
        modelData.put("password","123");
        return modelData;
    }

    //--------------------------------------------------------------------

    @RequestMapping("initbinder")
    /**
     * 请求地址是：
     *
     * controlleradvice/initbinder?username=222&password=123&state=1
     *
     * state是枚举类型，仍然可以映射成功,这得益于@initBinder初始化的数据绑定规则
     */
    public String initbinder(@ModelAttribute("binduser") User user){
        return "controlleradvice/test";
    }

    //使用实例2：InitBinder，使用该注解可以将前台传过来的数据进行转换
    @InitBinder
    public void intiBinder(WebDataBinder binder) {
        //注册State类型的编辑器
        binder.registerCustomEditor(State.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(State.valueOf(Integer.valueOf(text)));
            }
        });
    }

    //--------------------------------------------------------------------
    @RequestMapping("exceptionhandler")
    public String exceptionhandler(@ModelAttribute("binduser") User user){
        System.out.println(1/0);//error
        return "controlleradvice/test";
    }

    //使用示例3：ExceptionHandler,使用该注解实现全局异常处理
    /*@ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleValidationException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))){//ajax
            String message = e.getMessage();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            PrintWriter writer = response.getWriter();
            writer.write(message);
            writer.flush();
            writer.close();
        } else {
            response.sendRedirect("/error/500");
        }
    }*/
}
