package cn.xt.base.web.lib.aop;

import cn.xt.base.auth.model.ShiroUser;
import cn.xt.base.web.lib.controller.AdviceController;
import cn.xt.base.web.lib.model.Remoteaddr;
import cn.xt.base.web.lib.service.RemoteaddrService;
import org.apache.shiro.SecurityUtils;
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

    private static final Logger logger = LoggerFactory.getLogger(UrlInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try{
            String fullUrl = AdviceController.getFullUrl(request);
            ShiroUser principal = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
            Remoteaddr remoteaddr = new Remoteaddr();
            remoteaddr.setIp(request.getRemoteHost());
            remoteaddr.setTime(new Date());
            remoteaddr.setLoginid(principal==null?null:principal.getUserId());
            remoteaddr.setUrl(fullUrl);
            remoteaddrService.insert(remoteaddr);
        } catch (Exception e){
            logger.error("",e);
        }
        return super.preHandle(request, response, handler);
    }
}
