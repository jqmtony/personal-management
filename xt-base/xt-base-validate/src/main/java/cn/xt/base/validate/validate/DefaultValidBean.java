package cn.xt.base.validate.validate;

import org.hibernate.validator.constraints.*;

import javax.validation.constraints.*;
import java.util.Date;

/**
 * 测试BeanValidator内置注解和Hibernate validator内置注解
 */
public class DefaultValidBean {
    /**
     * BeanValidator内置注解
     */
    @Max(999) //允许最大值
    @Min(0)   //允许最小值
    private Long id;
    @Future //该字段时间必须比当前时间晚
    private Date futuredate;
    @Past   //该字段时间必须比当前时间早
    private Date pastdate;
    //约束精度，整数部分位数用integer指定，小数部分用fraction指定
    @Digits(integer = 2, fraction = 1)
    private Double decimal;
    @DecimalMax("10086")
    @DecimalMin("0")
    private String decimalString;
    @NotEmpty
    @Pattern(regexp = "[\\w\\d]+")
    private String remark;


    /**
     *Hibernate validator扩展的注解
     */
    @Email  //符合Email的格式
    private String email;
    @Range(min = 0,max = 100)
    private Integer range;
    @Length(min = 3,max = 6)
    private String len;
    @URL(protocol="http", port=22)
    private String url ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFuturedate() {
        return futuredate;
    }

    public void setFuturedate(Date futuredate) {
        this.futuredate = futuredate;
    }

    public Date getPastdate() {
        return pastdate;
    }

    public void setPastdate(Date pastdate) {
        this.pastdate = pastdate;
    }

    public Double getDecimal() {
        return decimal;
    }

    public void setDecimal(Double decimal) {
        this.decimal = decimal;
    }

    public String getDecimalString() {
        return decimalString;
    }

    public void setDecimalString(String decimalString) {
        this.decimalString = decimalString;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getRange() {
        return range;
    }

    public void setRange(Integer range) {
        this.range = range;
    }

    public String getLen() {
        return len;
    }

    public void setLen(String len) {
        this.len = len;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
