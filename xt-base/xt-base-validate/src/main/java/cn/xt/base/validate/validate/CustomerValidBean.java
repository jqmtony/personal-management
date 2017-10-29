package cn.xt.base.validate.validate;

//自定义验证器bean
public class CustomerValidBean {
    @Mobile
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
