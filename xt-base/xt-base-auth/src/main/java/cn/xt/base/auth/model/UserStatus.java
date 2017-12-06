package cn.xt.base.auth.model;

import cn.xt.base.model.NameCodeEnum;

/**
 * 用户状态
 * Created by heshun on 16/5/10.
 */
public enum UserStatus implements NameCodeEnum {
    normal(1,"正常"),
    disabled(2,"禁用");

    private final int code;
    private final String name;

    UserStatus(int code, String name) {
        this.code = code;
        this.name = name;
    }
    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public static UserStatus valueOf(int code) {
        for (UserStatus rs : UserStatus.values()) {
            if (rs.getCode() == code) {
                return rs;
            }
        }
        return null;
    }



}
