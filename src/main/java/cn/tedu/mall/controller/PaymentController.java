package cn.tedu.mall.controller;


import cn.tedu.mall.paypal.service.PaypalService;
import cn.tedu.mall.pojo.order.OrderAddNewDTO;
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
    private APIContext apiContext;
    @Autowired
    private PaypalService paypalService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IOrderRepository orderRepository;


    //http://localhost:9080/
    @GetMapping("/")
    @ApiOperation("支付首頁")
    @ApiOperationSupport(order = 100)
    @PreAuthorize("hasRole('user') or hasRole('admin')")
    public String index(){
        log.debug("開始訪問index");
        return "index";
    }

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
        //TODO 建立一個取消頁面
        return "cancel";
    }

    @GetMapping(PAYPAL_SUCCESS_URL)
    public String successPay(@RequestParam("paymentId") String paymentId,
                             @RequestParam("PayerID") String payerId,
                             @PathVariable Long userId){
        log.debug("開始訪問success");
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if(payment.getState().equals("approved")){
                //TODO 支付成功，連接創建訂單
                //TODO 連接到訂單詳情頁面
                OrderAddNewDTO orderAddNewDTO = orderRepository.getItem(userId);
                orderService.insert(orderAddNewDTO);
                return "redirect:http://localhost:8080/index";
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
       //TODO府款失敗
        return "cancel";
//        return "redirect:/";
    }

}