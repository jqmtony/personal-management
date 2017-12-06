package cn.xt.base.auth.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serializable;

/**
 * Created by heshun on 16-2-2.
 */
public class ShiroResource implements Serializable {
    private String url;
    private String icon;
    //菜单类型：1 菜单，2 资源
    private Integer type;
    //排序
    private Integer seq;
    private Long pid;
    private Long id;
    private String name;
    /**
     * 菜单打开方式
     */
    //(反序列化标识，用于把json字符串("{\"menuOpenWith\":{\"code\":1}}";)映射到指定类型(MenuOpenWith))
    //不加该注解则不能映射成功
    @JsonDeserialize(using = MenuOpenWithJsonDeserializer.class)
    private MenuOpenWith menuOpenWith;

    public MenuOpenWith getMenuOpenWith() {
        return menuOpenWith;
    }

    public void setMenuOpenWith(MenuOpenWith menuOpenWith) {
        this.menuOpenWith = menuOpenWith;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShiroResource that = (ShiroResource) o;

        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        if (icon != null ? !icon.equals(that.icon) : that.icon != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (seq != null ? !seq.equals(that.seq) : that.seq != null) return false;
        if (pid != null ? !pid.equals(that.pid) : that.pid != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return menuOpenWith == that.menuOpenWith;
    }

    @Override
    public int hashCode() {
        int result = url != null ? url.hashCode() : 0;
        result = 31 * result + (icon != null ? icon.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (seq != null ? seq.hashCode() : 0);
        result = 31 * result + (pid != null ? pid.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (menuOpenWith != null ? menuOpenWith.hashCode() : 0);
        return result;
    }
}
