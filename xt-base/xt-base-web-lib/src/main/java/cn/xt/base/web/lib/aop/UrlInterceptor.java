package cn.xt.base.web.lib.aop;

import cn.xt.base.auth.model.ShiroUser;
import cn.xt.base.util.IpUtil;
import cn.xt.base.web.lib.controller.AdviceController;
import cn.xt.base.web.lib.model.Remoteaddr;
import cn.xt.base.web.lib.service.RemoteaddrService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * create by xtao
 * create in 2017/11/12
 * 13:07
 */
public class UrlInterceptor extends HandlerInterceptorAdapter {
    @Resource
    private RemoteaddrService remoteaddrService;
    @Resource
    private SecurityManager securityManager;

    private static final Logger logger = LoggerFactory.getLogger(UrlInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try{
            String remoteHost = request.getRemoteHost();
            if (isLocalIp(remoteHost)) {
                return true;
            }
            String fullUrl = AdviceController.getFullUrl(request);
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
            remoteaddrService.insert(remoteaddr);
        } catch (Exception e){
            logger.error("",e);
        }
        return super.preHandle(request, response, handler);
    }

    private boolean isLocalIp(String ip){
        return "0:0:0:0:0:0:0:1".equals(ip) || "127.0.0.1".equals(ip);
    }
}
