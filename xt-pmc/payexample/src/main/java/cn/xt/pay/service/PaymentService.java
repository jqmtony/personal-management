package cn.xt.pay.service;

import cn.xt.base.model.Constant;
import cn.xt.pay.model.Payment;
import cn.xt.pay.model.PaymentVo;
import cn.xt.pay.util.PaymentUtil;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

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

    public static Connection getConnection() {
        Connection conn = null;
        try {

            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://120.24.245.15:3306/payment";
            String user = "root";
            String password = "root";
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (conn == null) {
           throw new RuntimeException("获取连接失败");
        }
        return conn;
    }

    public static void main(String[] args) {
        System.out.println(getConnection());
    }

    public boolean insertPayment(PaymentVo vo) throws SQLException, IOException {
        String filename = DateFormatUtils.ISO_DATE_FORMAT.format(new Date());
        File file = new File("C:\\db");
        if(!file.exists())file.mkdirs();
        OutputStream out = null;
        try
        {
            String path = (file.getAbsolutePath()+"/"+filename+".conf").replace("-","_");
            new File(path).createNewFile();
            out = new FileOutputStream(path,true);
            String line = vo.getAccountId()+"_"+vo.getName()+"_"+vo.getPurpose()+"_"+vo.getAmount();
            out.write((line+"\r\n").getBytes());
        } finally {
            if(out!=null)out.close();
        }
        return true;
        /*Connection conn = null;
        PreparedStatement ps = null;
        String sql = "insert into paymentinfo(accountId,name,cardNo,purpose,insertDate) values(?,?,?,?,NOW())";
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1,vo.getAccountId());
            ps.setString(2,vo.getName());
            ps.setString(3,vo.getCardNo());
            ps.setString(4,vo.getPurpose());
            int count = ps.executeUpdate();
            return count>0;
        } finally {
            if(ps!=null)ps.close();
            if(conn!=null)conn.close();
        }*/
    }
}
