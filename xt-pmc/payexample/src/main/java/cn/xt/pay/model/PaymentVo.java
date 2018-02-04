package cn.xt.pay.model;

import cn.xt.pay.util.PaymentUtil;

import java.security.NoSuchAlgorithmException;

/**
 * create by xt
 */
public class PaymentVo {
    private String name;
    private String cardNo;
    private String purpose;
    private String amount;
    private String accountId = "2120180118140812001";
    private String orderId = PaymentUtil.getOrderNumber();
    private String responseUrl = "http://120.24.245.15:8888/pay/payCallback.jsp";
    private String mac;

    public static final String URL = "182.92.227.135:9620/delegate-pay-front/delegatePay/pay";

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getResponseUrl() {
        return responseUrl;
    }

    public void setResponseUrl(String responseUrl) {
        this.responseUrl = responseUrl;
    }

    public String getMac() throws NoSuchAlgorithmException {
        return PaymentUtil.encryptMd52Upper("accountId="+accountId
                +"&name="+name
                +"&cardNo="+cardNo
                +"&orderId="+orderId
                +"&purpose="+purpose
                +"&amount="+amount
                +"&responseUrl="+responseUrl);
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
