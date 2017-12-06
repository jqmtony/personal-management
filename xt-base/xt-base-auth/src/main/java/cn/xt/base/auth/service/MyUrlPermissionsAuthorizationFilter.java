package cn.xt.base.auth.service;


import cn.xt.base.auth.model.ShiroUser;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 *  全局的判断当前请求是否有权限访问
 */
public class MyUrlPermissionsAuthorizationFilter extends PermissionsAuthorizationFilter {


    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String servletPath = httpServletRequest.getServletPath();
        Subject subject = getSubject(request, response);
        ShiroUser shiroUser = (ShiroUser)subject.getPrincipal();
        if("admin".equals(shiroUser.getUsername())){
            return true;
        }
        return subject.isPermitted(servletPath);
    }
}
