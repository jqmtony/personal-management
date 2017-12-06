package cn.xt.base.auth.model;


import cn.xt.base.model.NameCodeEnum;

/**
 * 菜单位置
 * Created by heshun on 16-9-5.
 */
public enum MenuLocation implements NameCodeEnum {

    left(1, "左边"), top(2, "顶部");

    private final int code;
    private final String name;

    MenuLocation(int code, String name) {
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

    public static MenuLocation valueOf(int code) {
        for (MenuLocation rs : MenuLocation.values()) {
            if (rs.getCode() == code) {
                return rs;
            }
        }
        return null;
    }
}
