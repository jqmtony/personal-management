package cn.xt.base.web.lib.controller;

import cn.xt.base.auth.model.ShiroUser;
import org.apache.shiro.SecurityUtils;

/**
 * create by xtao
 * create in 2017/12/4 0:54
 */
public abstract class BaseController {
    /**
     * 获取登陆用户的id
     *
     * @return
     */
    protected Long getPrincipalId() {
        ShiroUser principal = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        if(principal != null){
            return principal.getUserId();
        }
        return null;
    }
}
