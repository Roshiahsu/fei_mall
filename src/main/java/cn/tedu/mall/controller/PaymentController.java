package cn.tedu.mall.controller;


import cn.tedu.mall.paypal.service.PaypalService;
import cn.tedu.mall.pojo.order.OrderAddNewDTO;
import cn.tedu.mall.pojo.order.OrderAddVO;
import cn.tedu.mall.repository.IOrderRepository;
import cn.tedu.mall.service.IOrderService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName PaymentController
 * @Version 1.0
 * @Description TODO
 * @Date 2023/1/28、上午11:25
 */
@Controller
@RequestMapping("/paypal")
@Slf4j
@Api(tags = "Paypal金流管理模組")
public class PaymentController {

    public static final String PAYPAL_SUCCESS_URL = "/{userId}/success";
    public static final String PAYPAL_CANCEL_URL = "/cancel";

    @Autowired
    private PaypalService paypalService;

    @PostMapping("/pay")
    @ApiOperation("支付請求")
    @ApiOperationSupport(order = 120)
    public String pay(HttpServletRequest request){
        log.debug("獲取請求，開始pay");
        return paypalService.pay(request);
    }

    @GetMapping(PAYPAL_CANCEL_URL)
    public String cancelPay(){
        log.debug("開始訪問cancel");
        return "redirect:http://18.206.208.196:8088/cancel";
    }

    @GetMapping(PAYPAL_SUCCESS_URL)
    public String successPay(@RequestParam("paymentId") String paymentId,
                             @RequestParam("PayerID") String payerId,
                             @PathVariable Long userId){
        log.debug("開始訪問success");
        String result = paypalService.successPay(paymentId, payerId, userId);

        return result;
    }

}