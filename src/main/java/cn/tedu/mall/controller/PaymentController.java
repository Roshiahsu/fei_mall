package cn.tedu.mall.controller;


import cn.tedu.mall.paypal.URLUtils;
import cn.tedu.mall.paypal.config.PaypalPaymentIntent;
import cn.tedu.mall.paypal.config.PaypalPaymentMethod;
import cn.tedu.mall.paypal.service.PaypalService;
import cn.tedu.mall.web.JsonResult;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName PaymentController
 * @Version 1.0
 * @Description TODO
 * @Date 2023/1/28、上午11:25
 */
@RestController
@RequestMapping("/paypal")
public class PaymentController {

    public static final String PAYPAL_SUCCESS_URL = "/success";
    public static final String PAYPAL_CANCEL_URL = "/cancel";

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private APIContext apiContext;
    @Autowired
    private PaypalService paypalService;

    //http://localhost:9080/paypal/
    @GetMapping("/")
    public String index(Model model){
        log.debug("開始訪問index");
        return "index";
    }

    @PostMapping("/pay")
    public JsonResult pay(HttpServletRequest request){
        log.debug("獲取請求，開始pay");
        String cancelUrl = URLUtils.getBaseURl(request) + "/paypal" + PAYPAL_CANCEL_URL;
        String successUrl = URLUtils.getBaseURl(request) + "/paypal" + PAYPAL_SUCCESS_URL;

        try {
            Payment payment = paypalService.createPayment(
                    20.00,
                    "USD",
                    PaypalPaymentMethod.paypal,
                    PaypalPaymentIntent.sale,
                    "測試用 description",
                    cancelUrl,
                    successUrl);

            for(Links links : payment.getLinks()){
                log.debug("links>>>{}",links);
                if(links.getRel().equals("approval_url")){
                    log.debug("links.getHref()>>>{}",links.getHref());
//                    return "redirect:" + links.getHref();
                    return JsonResult.ok(links.getHref());
                }
            }

            } catch (PayPalRESTException payPalRESTException) {
            payPalRESTException.printStackTrace();
        }
//        return "redirect:/";
        return JsonResult.ok();
    }

    @GetMapping(PAYPAL_CANCEL_URL)
    public String cancelPay(){
        log.debug("開始訪問cancel");
        return "cancel";
    }

    @GetMapping(PAYPAL_SUCCESS_URL)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId){
        log.debug("開始訪問success");
       try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if(payment.getState().equals("approved")){
                return "redirect:http://localhost:8080/index";
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:/";
    }

}