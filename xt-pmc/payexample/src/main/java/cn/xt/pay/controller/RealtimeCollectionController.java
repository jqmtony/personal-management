package cn.xt.pay.controller;

import cn.xt.base.util.StringUtil;
import cn.xt.pay.model.Payment;
import cn.xt.pay.model.PaymentVo;
import cn.xt.pay.service.PaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

/**
 * 实时代付
 * create by xt
 */
@RequestMapping("delegate-pay-front/delegatePay")
@Controller
public class RealtimeCollectionController {

    private PaymentService paymentService = new PaymentService();

    @RequestMapping(value = "pay",method = RequestMethod.POST)
    public String pay(PaymentVo vo, HttpServletRequest request) throws NoSuchAlgorithmException, SQLException, IOException {
        if(StringUtil.isEmpty(vo.getName())
                || StringUtil.isEmpty(vo.getAmount())
                || StringUtil.isEmpty(vo.getAccountId())
                || StringUtil.isEmpty(vo.getCardNo())
                || StringUtil.isEmpty(vo.getPurpose())){
            return "redirect:/demo/index.jsp";
        }
        boolean isSuc = paymentService.insertPayment(vo);
        if(isSuc){
            Payment payment = paymentService.generatePayment(request,"实时代付",vo.getAmount());
            request.setAttribute("payment",payment);
            return "pay/payIndex";
        }
        return "redirect:/demo/index.jsp";
    }
}
