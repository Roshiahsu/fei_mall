package cn.tedu.mall.paypal;

import cn.tedu.mall.pojo.order.OrderAddNewDTO;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName IPaypalService
 * @Version 1.0
 * @Description TODO
 * @Date 2023/2/3、下午2:41
 */
public interface IPaypalService {

    public static final String HOST_URL ="http://18.206.208.196:8088";

    String pay(HttpServletRequest request);

    String successPay(String paymentId,String payerId,Long userId);
}
