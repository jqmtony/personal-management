package cn.xt.pay.service;

import cn.xt.pay.model.Payment;
import cn.xt.pay.util.PaymentUtil;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;

public class PaymentService {
    public Payment generatePayment(HttpServletRequest request,String goodsName, String ammout) throws NoSuchAlgorithmException {
        String callBackURL = PaymentUtil.getFullReqUrl(request,"pay/payCallback.jsp");
        String notifyURL = PaymentUtil.getFullReqUrl(request,"pay/paySuccess.jsp");
        String orderNumber = PaymentUtil.getOrderNumber();
        String applyDate = PaymentUtil.getApplyDate();

        Payment p = new Payment();
        p.setGoodsName(goodsName);
        p.setAmmount(ammout);
        p.setApplyDate(applyDate);
        p.setOrderNumber(orderNumber);
        p.setCallbackUrl(callBackURL);
        p.setNotifyUrl(notifyURL);
        String sign = PaymentUtil.encryptMd52Upper(
                Payment.NOTIFY_URL_PLACEHODER
                .replace(Payment.APPLY_DATE_PLACEHODER,applyDate)
                .replace(Payment.CALLBACK_URL_PLACEHODER,callBackURL)
                .replace(Payment.NOTIFY_URL_PLACEHODER,notifyURL)
                .replace(Payment.ORDER_NUMBER_PLACEHODER,orderNumber)
        );
        p.setSign(PaymentUtil.encryptMd52Upper(sign));
        return p;
    }
}
