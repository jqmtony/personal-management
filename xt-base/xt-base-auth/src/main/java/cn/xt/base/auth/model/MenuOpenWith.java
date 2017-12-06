package cn.xt.base.auth.model;


import cn.xt.base.model.NameCodeEnum;

/**
 * 菜单打开方式
 * Created by heshun on 16-9-5.
 */
public enum MenuOpenWith implements NameCodeEnum {

    center(1, "内容区"), newTab(2, "新标签页");

    private final int code;
    private final String name;

    MenuOpenWith(int code, String name) {
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

    public static MenuOpenWith valueOf(int code) {
        for (MenuOpenWith rs : MenuOpenWith.values()) {
            if (rs.getCode() == code) {
                return rs;
            }
        }
        return null;
    }
}
