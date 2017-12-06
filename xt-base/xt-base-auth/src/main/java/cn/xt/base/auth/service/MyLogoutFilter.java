package cn.xt.base.auth.service;

import cn.xt.base.auth.model.ShiroUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.crazycake.shiro.RedisManager;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 注销拦截器
 * Created by heshun on 2016/11/17.
 */
public class MyLogoutFilter extends LogoutFilter {

    private RedisManager redisManager;

    //退出之前的操作
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        beforeLogout();
        return super.preHandle(request, response);
    }

    public void beforeLogout() {
        Subject subject = SecurityUtils.getSubject();
        ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
        if (StringUtils.hasText(shiroUser.getUsername())) {
            //这里可以执行清空用户登录信息缓存操作
            redisManager.del(shiroUser.getUsername().getBytes());
        }
    }

    public RedisManager getRedisManager() {
        return redisManager;
    }

    public void setRedisManager(RedisManager redisManager) {
        this.redisManager = redisManager;
    }
}
