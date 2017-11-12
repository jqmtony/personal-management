package cn.xt.pmc.management.aop;

import cn.xt.base.web.lib.data.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * create by xtao
 * create in 2017/11/12
 * 13:07
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    private static final String[] excludeURL = {"/error/404","/login", "/error/500"};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String servletPath = request.getServletPath();
        for (String eUrl : excludeURL) {
            if (servletPath.contains(eUrl)) {
                return true;
            }
        }

        Object obj = request.getSession().getAttribute("currentUser");
        if (obj == null){
            response.sendRedirect("error/500");
            return  false;
        }
        String user = (String) request.getSession().getAttribute("currentUser");
        if (user == null) {
            response.sendRedirect("error/500");
            return  false;
        }
        return super.preHandle(request, response, handler);
    }
}
