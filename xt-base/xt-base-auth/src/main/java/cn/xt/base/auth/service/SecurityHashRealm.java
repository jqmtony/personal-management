package cn.xt.base.auth.service;

import cn.xt.base.auth.model.ShiroUser;
import cn.xt.base.auth.simulate.SimulateDbData;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.util.StringUtils;

import java.util.List;

public class SecurityHashRealm extends AuthorizingRealm {

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        String userName = principalCollection.getPrimaryPrincipal().toString();
        String roleName = SimulateDbData.getDbRoleNames(userName);
        if(StringUtils.hasText(roleName))return null;
        info.addRole(roleName);

        List<String> permissionUrls = SimulateDbData.getDbPermissionUrls(roleName);
        if(CollectionUtils.isEmpty(permissionUrls))return null;
        info.addStringPermissions(permissionUrls);

        return info;
    }

    @Override//认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String userName = usernamePasswordToken.getUsername();

        ShiroUser shiroUser = SimulateDbData.getUser(userName);

        String salt = SimulateDbData.getDbSalt(userName);
        String password = SimulateDbData.getDbPwd(salt);

        //使用自定义ByteSource解决shiro缓存认证信息时，序列化/反序列化 报错的问题
        ByteSource byteSalt = new SerializableSimpleByteSource(salt);

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(shiroUser,password,this.getName());
        info.setCredentialsSalt(byteSalt);
        return info;
    }
}
