package cn.xt.base.web.lib.controller;

import cn.xt.base.cfgcenter.config.SystemConfig;
import cn.xt.base.web.lib.data.State;
import cn.xt.base.web.lib.data.User;
import cn.xt.base.web.lib.model.IndexCallback;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *  ControllerAdvice注解应用举例
 */
@ControllerAdvice
@RequestMapping("controlleradvice")
public class AdviceController {
    protected Logger logger = LoggerFactory.getLogger(AdviceController.class);

    public static final List<IndexCallback> INDEX_CALLBACKS = new LinkedList<>();

    public static String getFullUrl(HttpServletRequest request){
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int port = request.getServerPort();
        String path = request.getContextPath();
        String basePath = scheme + "://" + serverName + ":" + port + path;
        String servletPath = request.getServletPath();
        return basePath + servletPath;
    }

    @RequestMapping("modelattrpage")
    public String modelpage(Model model){
        //这里并没有model.setAttribute或者request.setAttribute来设置user,但是依然可以使用${user}获取
        model.addAttribute("user","aaa");
        return "controlleradvice/test";
    }

    //使用实例1：ModelAttribute，使用该注解可以为所有Controller设置通用的model属性
    @ModelAttribute("siteInfo")
    public Map<String,Object> setModelAttr(){
        Map<String,Object> modelData = new HashMap<String,Object>();
        modelData.put("titlePrefix", SystemConfig.titlePrefix);
        modelData.put("domainName",SystemConfig.domainName);
        modelData.put("defaultKeywords",SystemConfig.defaultKeywords);
        modelData.put("defaultDescription",SystemConfig.defaultDescription);
        return modelData;
    }

    @ModelAttribute("ctx")
    public String setModelAttr(HttpServletRequest request){
        return request.getContextPath();
    }

    @ModelAttribute("fullPath")
    public String setfullPath(HttpServletRequest request){
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int port = request.getServerPort();
        String path = request.getContextPath();
        String basePath = scheme + "://" + serverName + ":" + port + path;
        return basePath;
    }

    /**
     * 设置下拉菜单列表
     */
    @ModelAttribute("menus")
    public List setBlogTypeMenus(HttpServletRequest request){
        if(INDEX_CALLBACKS.size()!=0){
            return INDEX_CALLBACKS.get(0).getHeaderMenus();
        }
        return null;
    }


    @ModelAttribute("fullUrl")
    public String setRequestUrl(HttpServletRequest request){
        return getFullUrl(request);
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

    /**
     * 授权失败跳转到Login
     * @param e
     * @param request
     * @param response
     * @throws IOException
     */
    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleValidationAuthorizationException(AuthorizationException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("/login");
    }

    /**
     * 服务端异常统一处理
     *
     * @param e
     * @param request
     * @param response
     * @throws IOException
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleValidationException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.error("服务端异常", e);
        if (isAjax(request)) {
            String message = e.getMessage();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            writeMsg(response, message);
        } else {
            response.sendRedirect("/error/500");
        }
    }

    /**
     * 判断请求是否是ajax请求
     *
     * @param request
     * @return true表示是ajax请求
     */
    private boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }

    /**
     * 在响应中写入消息
     *
     * @param response
     * @param msg
     * @throws IOException
     */
    private void writeMsg(HttpServletResponse response, String msg) throws IOException {
        PrintWriter writer = response.getWriter();
        if (StringUtils.isBlank(msg)){
            return;
        }
        writer.write(msg);
        writer.flush();
        writer.close();
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
