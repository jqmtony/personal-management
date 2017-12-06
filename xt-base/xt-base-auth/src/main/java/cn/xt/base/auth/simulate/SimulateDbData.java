package cn.xt.base.auth.simulate;

import cn.xt.base.auth.model.ShiroRole;
import cn.xt.base.auth.model.ShiroUser;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//模拟数据库数据
public class SimulateDbData {
    static Map<Long,ShiroUser> userMap = null;
    static Map<String,String> userRoleMap = null;
    static Map<String,List<String>> userUrlMap = null;
    static {
        if(userMap==null){
            userMap = new HashMap<>();
        }
        if(userRoleMap==null){
            userRoleMap = new HashMap<>();
        }
        if(userUrlMap==null){
            userUrlMap = new HashMap<>();
        }

        ShiroRole role = new ShiroRole();
        role.setName("超级管理员");
        role.setId(1L);

        ShiroUser admin = new ShiroUser();
        admin.setUserId(1L);
        admin.setUsername("admin");
        admin.setRealname("ADMIN");
        admin.setPassword(getDbPwd(getDbSalt(admin.getUsername())));
        admin.setRoles(Arrays.asList(role));
        admin.setMobile("13350999900");
        userMap.put(1L,admin);
    }

    public static ShiroUser getUser(String userName){
        for(Map.Entry<Long,ShiroUser> entry : userMap.entrySet()){
            if(userName.equals(entry.getValue().getUsername())){
                return entry.getValue();
            }
        }
        return null;
    }

    public static String getDbSalt(String userName){
        String rdm = new SecureRandomNumberGenerator().nextBytes().toHex();
        return userName + rdm;
    }

    public static String getDbPwd(String salt){
        String realPwd = "123";
        SimpleHash simpleHash = new SimpleHash("md5",realPwd, salt,2);
        return simpleHash.toHex();
    }

    public static String getDbRoleNames(String userName){
        return userRoleMap.get(userName);
    }

    public static List<String> getDbPermissionUrls(String roleName){
        return userUrlMap.get(roleName);
    }
}
