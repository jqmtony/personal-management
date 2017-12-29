package cn.xt.pmc.management.util;

import cn.xt.base.auth.model.ShiroUser;
import org.apache.shiro.SecurityUtils;

public class ShiroUtil {
    /**
     * 获取登陆用户的id
     *
     * @return
     */
    public static Long getPrincipalId() {
        ShiroUser principal = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        if(principal != null){
            return principal.getUserId();
        }
        return null;
    }
}
