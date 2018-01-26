package cn.xt.base.web.lib.aop;


import cn.xt.base.auth.model.ShiroUser;
import cn.xt.base.util.IpUtil;
import cn.xt.base.web.lib.controller.AdviceController;
import cn.xt.base.web.lib.model.Remoteaddr;
import cn.xt.base.web.lib.service.RemoteaddrService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 *  全局的判断当前请求是否有权限访问
 */
public class MyUrlPermissionsAuthorizationFilter extends PermissionsAuthorizationFilter {
    @Resource
    private RemoteaddrService remoteaddrService;
    @Resource
    private SecurityManager securityManager;

    private static final Logger logger = LoggerFactory.getLogger(UrlInterceptor.class);

    @Override
    public boolean isAccessAllowed(ServletRequest req, ServletResponse response, Object mappedValue) throws IOException {
        try{
            HttpServletRequest request = (HttpServletRequest) req;

            String fullUrl = AdviceController.getFullUrl(request);

            //获取远程地址
            String remoteHost = request.getRemoteHost();
            if (isLocalIp(remoteHost)) {
                return true;
            }
            SecurityUtils.setSecurityManager(securityManager);
            ShiroUser principal = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
            Remoteaddr remoteaddr = new Remoteaddr();
            remoteaddr.setIp(remoteHost);
            if(IpUtil.isIpv4(remoteHost)){
                remoteaddr.setHomeloc(IpUtil.IpInfo.getIpHomeLocation(remoteHost));
                remoteaddr.setIsp(IpUtil.IpInfo.getIpIsp(remoteHost));
            }
            remoteaddr.setTime(new Date());
            remoteaddr.setLoginid(principal==null?null:principal.getUserId());
            remoteaddr.setUrl(fullUrl);
            remoteaddr.setParam(getParamStr(request));
            remoteaddrService.insert(remoteaddr);
        } catch (Exception e){
            logger.error("",e);
        }
        return true;
    }

    private String getParamStr(HttpServletRequest request){
        //参数组装
        StringBuffer paramStr = new StringBuffer();
        Map<String,String> parameterMap = getAllReqParams(request);
        for(Map.Entry<String, String> entry : parameterMap.entrySet()){
            paramStr.append(entry.getKey()+"="+entry.getValue().toString()+",");
        }
        if(paramStr.length()!=0){
            paramStr.deleteCharAt(paramStr.length()-1);

            paramStr.insert(0,"[");
            if(AdviceController.isAjax(request)){//不记录ajax请求
                paramStr.insert(0,"Ajax:");
            }
            paramStr.append("]");
        }
        return paramStr.toString();
    }

    private Map<String,String> getAllReqParams(HttpServletRequest request){
        Map<String,String> map = new HashMap<>();
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String key = (String) paramNames.nextElement();
            String[] values = request.getParameterValues(key);
            if (values.length == 1) {
                String val = values[0];
                map.put(key,val);
            }
        }
        return map;
    }

    private boolean isLocalIp(String ip){
        return "0:0:0:0:0:0:0:1".equals(ip) || "127.0.0.1".equals(ip);
    }

    /*
        //权限控制
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String servletPath = httpServletRequest.getServletPath();
        Subject subject = getSubject(request, response);
        ShiroUser shiroUser = (ShiroUser)subject.getPrincipal();
        if("admin".equals(shiroUser.getUsername())){
            return true;
        }
        return subject.isPermitted(servletPath);

    */
}
