package cn.xt.base.validate.model;

import cn.xt.base.validate.validate.GroupValidBean;
import cn.xt.base.validate.validate.Mobile;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.swing.text.html.HTML;
import javax.validation.GroupSequence;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.groups.ConvertGroup;
import java.util.Date;

/*
* 设置分组验证的顺序，即当存在多个组同时验证时，先验证Tag1组，在验证Tag2组，
* 最后验证ValidBean的默认组
* */
@GroupSequence({Tag1.class,Tag2.class,ValidBean.class})
public class ValidBean {

    private Long id;
    private Date date;
    @Mobile
    private String email;
    /*@Mobile.List({
        @Mobile(regexp = "[A-z0-9]+"),
        @Mobile(regexp = "\\w+")
    })*/
//    @Mobile(regexp = "\\w+")
    private String phone;

    //嵌套验证，表示验证ValidBean对象时，同时验证GroupValidBean对象
    @Valid
    /*
        当使用Tag2分组验证ValidBean对象时，使用Tag1组验证GroupValidBean对象,
        如果不配置，则用哪个组验证ValidBean对象，也用哪个组验证GroupValidBean对象
     */
//    @ConvertGroup(from = Tag2.class, to = Tag1.class)
    private GroupValidBean groupValidBean;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public GroupValidBean getGroupValidBean() {
        return groupValidBean;
    }

    public void setGroupValidBean(GroupValidBean groupValidBean) {
        this.groupValidBean = groupValidBean;
    }
}
