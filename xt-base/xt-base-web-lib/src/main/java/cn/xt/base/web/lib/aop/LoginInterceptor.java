package cn.xt.base.web.lib.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * create by xtao
 * create in 2017/11/12
 * 13:07
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try{
            String loginPage = request.getContextPath()+"/toLogin";
            Object obj = request.getSession().getAttribute("currentUser");
            if (obj == null){
                response.sendRedirect(loginPage);
                return  false;
            }
            String user = (String) request.getSession().getAttribute("currentUser");
            if (user == null) {
                response.sendRedirect(loginPage);
                return  false;
            }
        } catch (Exception e){
            logger.error("",e);
            response.sendRedirect(request.getContextPath()+"/error/500");
            return  false;
        }
        return super.preHandle(request, response, handler);
    }
}
