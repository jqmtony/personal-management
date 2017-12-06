package cn.xt.base.auth.model;

import java.io.Serializable;
import java.util.List;

public class ShiroUser implements Serializable {

    private Long userId;
    private String username;
    private String password;
    private UserStatus status;
    private String realname;
    private String mobile;
    private List<ShiroRole> roles;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ShiroRole> getRoles() {
        return roles;
    }

    public void setRoles(List<ShiroRole> roles) {
        this.roles = roles;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }
}
