package cn.tedu.mall.paypal.service;


import cn.tedu.mall.paypal.IPaypalService;
import cn.tedu.mall.paypal.URLUtils;
import cn.tedu.mall.paypal.config.PaypalPaymentIntent;
import cn.tedu.mall.paypal.config.PaypalPaymentMethod;
import cn.tedu.mall.pojo.order.OrderAddNewDTO;
import cn.tedu.mall.pojo.order.OrderAddVO;
import cn.tedu.mall.repository.IOrderRepository;
import cn.tedu.mall.service.IOrderService;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


/**
 * @ClassName PaypalService
 * @Version 1.0
 * @Description TODO
 * @Date 2023/1/28、上午11:26
 */
@Service
@Slf4j
public class PaypalService implements IPaypalService {

    public static final String PAYPAL_SUCCESS_URL = "/success";
    public static final String PAYPAL_CANCEL_URL = "/cancel";

    @Autowired
    private APIContext apiContext;

    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private IOrderService orderService;


    @Override
    public String pay(HttpServletRequest request) {
        log.debug("獲取請求，開始pay");
        OrderAddNewDTO orderAddNewDTO = getOrderInfo(request);
        Long userId = orderAddNewDTO.getUserId();

        String cancelUrl = URLUtils.getBaseURl(request) + "/paypal" + PAYPAL_CANCEL_URL;
        String successUrl = URLUtils.getBaseURl(request) + "/paypal/" +userId+ PAYPAL_SUCCESS_URL;
        Double amountOfActualPay = Double.valueOf(request.getParameter("AmountOfActualPay"));

        orderRepository.addItem(orderAddNewDTO);

        try {
            Payment payment = createPayment(
                    amountOfActualPay,
                    "TWD",
                    PaypalPaymentMethod.paypal,
                    PaypalPaymentIntent.sale,
                    "測試用 description",
                    cancelUrl,
                    successUrl);

            for(Links links : payment.getLinks()){
                log.debug("links>>>{}",links);
                if(links.getRel().equals("approval_url")){
                    log.debug("links.getHref()>>>{}",links.getHref());
                    return "redirect:" + links.getHref();
                }
            }

        } catch (PayPalRESTException payPalRESTException) {
            payPalRESTException.printStackTrace();
        }
        return "redirect:http://localhost:8080/failed";
    }

    @Override
    public String successPay(String paymentId, String payerId, Long userId) {
        try {
            Payment payment = executePayment(paymentId, payerId);
            if(payment.getState().equals("approved")){
                //從redis中獲取訂單詳情
                OrderAddNewDTO orderAddNewDTO = orderRepository.getItem(userId);
                //新增訂單
                OrderAddVO orderAddVO = orderService.insert(orderAddNewDTO);
                Long id = orderAddVO.getId();
                //刪除redis中的資料
                orderRepository.deleteItem(userId);
                return "redirect:http://localhost:8080/orderDetailInfo?id="+id;
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:http://localhost:8080/cancel";
    }

    /**
     * 創建付款資訊
     * @param total 實際支付金額
     * @param currency 支付貨幣
     * @param method 支付方式
     * @param intent 創建種類
     * @param description 描述
     * @param cancelUrl 取消發送的請求
     * @param successUrl 成功發送的請求
     * @return
     * @throws PayPalRESTException
     */
    public Payment createPayment(
            Double total,
            String currency,
            PaypalPaymentMethod method,
            PaypalPaymentIntent intent,
            String description,
            String cancelUrl,
            String successUrl) throws PayPalRESTException {
        log.debug("開始封裝Payment");
        Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setTotal(String.format("%.2f", total));

        log.debug("開始封裝transaction");
        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(method.toString());

        Payment payment = new Payment();
        payment.setIntent(intent.toString());
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        log.debug("開始封裝網址");
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);
        return payment.create(apiContext);
    }

    public OrderAddNewDTO getOrderInfo(HttpServletRequest request){
        //獲取userId
        String userIdString=request.getParameter("userId");
        Long userId = Long.valueOf(userIdString);
        //獲取實際支付金額
        String amountOfActualPayString = request.getParameter("AmountOfActualPay");
        BigInteger aap =new BigInteger(amountOfActualPayString);
        log.debug("AmountOfActualPay>>>{}",amountOfActualPayString);
        //獲取原始價錢
        String amountOfOriginalPrice = request.getParameter("amountOfOriginalPrice");
        BigInteger aoo =new BigInteger(amountOfOriginalPrice);
        log.debug("amountOfOriginalPrice>>>{}",amountOfOriginalPrice);
        //獲取收件人姓名
        String recipientName = request.getParameter("recipientName");
        log.debug("recipientName>>>{}",recipientName);
        //獲取收件人手機
        String recipientPhone = request.getParameter("recipientPhone");
        log.debug("recipientPhone>>>{}",recipientPhone);
        //獲取收件人地址
        String recipientAddress = request.getParameter("recipientAddress");
        log.debug("recipientAddress>>>{}",recipientAddress);

        OrderAddNewDTO orderAddNewDTO = new OrderAddNewDTO();
        orderAddNewDTO.setUserId(userId);
        orderAddNewDTO.setAmountOfActualPay(aap);
        orderAddNewDTO.setAmountOfOriginalPrice(aoo);
        orderAddNewDTO.setRecipientName(recipientName);
        orderAddNewDTO.setRecipientPhone(recipientPhone);
        orderAddNewDTO.setRecipientAddress(recipientAddress);
        return orderAddNewDTO;
    }

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException{
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        return payment.execute(apiContext, paymentExecute);
    }
}
