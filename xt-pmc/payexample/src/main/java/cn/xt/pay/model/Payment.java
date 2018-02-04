package cn.xt.pay.model;

public class Payment {
    private String orderNumber;
    private String applyDate;
    private String notifyUrl;
    private String callbackUrl;
    private String sign;
    private String goodsName;
    private String ammount;

    public static final String APPLY_DATE_PLACEHODER = "{appDate}";
    public static final String PAY_AMOUNT_PLACEHODER = "{payAmmout}";
    public static final String CALLBACK_URL_PLACEHODER = "{callUrl}";
    public static final String NOTIFY_URL_PLACEHODER = "{notfyUrl}";
    public static final String ORDER_NUMBER_PLACEHODER = "{orderNumber}";

    public static final String APP_KEY = "fj6m9i769p2jdby3evjiinniw0kuro8w";
    public static final String MEMBER_ID = "10066";
    public static final String AUTHENTICATION_URL = "https://oa.399ex.net/Pay_Index.html";
    public static final String SIGN_Url = "pay_amount="+ PAY_AMOUNT_PLACEHODER
            +"&pay_applydate="+APPLY_DATE_PLACEHODER
            +"&pay_callbackurl="+ CALLBACK_URL_PLACEHODER
            +"&pay_memberid="+MEMBER_ID
            +"&pay_notifyurl="+NOTIFY_URL_PLACEHODER
            +"&pay_orderid="+ORDER_NUMBER_PLACEHODER
            +"&key="+APP_KEY;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getAmmount() {
        return ammount;
    }

    public void setAmmount(String ammount) {
        this.ammount = ammount;
    }
}
