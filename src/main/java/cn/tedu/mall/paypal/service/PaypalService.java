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
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;


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
    public static final String RECIPIENT_ADDRESS_DETAIL_ADDRESS = "detailAddress";
    public static final String RECIPIENT_ADDRESS_CITY = "city";
    public static final String RECIPIENT_ADDRESS_POSTAL_CODE = "postalCode";

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
        //獲取收件人地址
        String address = request.getParameter("recipientAddress");

        try {
            Payment payment = createPayment(
                    amountOfActualPay,
                    "TWD",
                    PaypalPaymentMethod.paypal,
                    PaypalPaymentIntent.sale,
                    address,
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
        return "redirect:"+HOST_URL+"/failed";
    }

    @Override
    public String successPay(String paymentId, String payerId, Long userId) {
        try {
            Payment payment = executePayment(paymentId, payerId);
            if(payment.getState().equals("approved")){
                //從redis中獲取訂單詳情
                OrderAddNewDTO orderAddNewDTO = orderRepository.getItem(userId);
                log.debug("orderAddNewDTO>>>{}",orderAddNewDTO);
                //新增訂單
                OrderAddVO orderAddVO = orderService.insert(orderAddNewDTO);
                Long id = orderAddVO.getId();
                //刪除redis中的資料
                orderRepository.deleteItem(userId);
//                return "redirect:"+HOST_URL+"/orderDetailInfo?id="+id;
                return "redirect:"+HOST_URL;
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:"+HOST_URL+"/cancel";
    }

    /**
     * 創建付款資訊
     * @param total 實際支付金額
     * @param currency 支付貨幣
     * @param method 支付方式
     * @param intent 創建種類
     * @param address 地址
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
            String address,
            String description,
            String cancelUrl,
            String successUrl) throws PayPalRESTException {
        log.debug("開始封裝Payment");
        Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setTotal(String.format("%.2f", total));

        //購買商品
        log.debug("開始封裝transaction");
        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        log.debug("獲取到的地址>>>{}",address);
        Map<String, String> addressMap = parseAddress(address);
        // 設置地址
        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setLine1(addressMap.get(RECIPIENT_ADDRESS_DETAIL_ADDRESS));
        shippingAddress.setCity(addressMap.get(RECIPIENT_ADDRESS_CITY));
        shippingAddress.setPostalCode(addressMap.get(RECIPIENT_ADDRESS_POSTAL_CODE));
        shippingAddress.setCountryCode("TW");


        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        ItemList itemList = new ItemList();
        itemList.setShippingAddress(shippingAddress);
        transaction.setItemList(itemList);
        //設定付款方式
        Payer payer = new Payer();
        payer.setPaymentMethod(method.toString());

        //設置付款重定向 URL
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);

        Payment payment = new Payment();
        payment.setIntent(intent.toString());
        payment.setPayer(payer);
        payment.setTransactions(Arrays.asList(transaction));

        log.debug("開始封裝網址");
        payment.setRedirectUrls(redirectUrls);
        return payment.create(apiContext);
    }

    /**
     *  解析地址
     * @param address
     * @return
     */
    public Map<String,String> parseAddress(String address){
        Map<String,String> addressMap = new HashMap<>();
        //郵遞區號
        String postalCode = address.substring(0, 0 + 3);
        //城市
        String city = address.substring(3,3+3);
        //detailAddress
        String detailAddress = address.substring(9);
        addressMap.put(RECIPIENT_ADDRESS_POSTAL_CODE,postalCode);
        addressMap.put(RECIPIENT_ADDRESS_CITY,city);
        addressMap.put(RECIPIENT_ADDRESS_DETAIL_ADDRESS,detailAddress);

        return addressMap;
    }

    public OrderAddNewDTO getOrderInfo(HttpServletRequest request){
        //獲取userId
        String userIdString=request.getParameter("userId");
        Long userId = Long.valueOf(userIdString);
        //獲取實際支付金額
        String amountOfActualPayString = request.getParameter("AmountOfActualPay");
        BigDecimal aap =new BigDecimal(amountOfActualPayString);
        log.debug("AmountOfActualPay>>>{}",amountOfActualPayString);
        //獲取原始價錢
        String amountOfOriginalPrice = request.getParameter("amountOfOriginalPrice");
        BigDecimal aoo =new BigDecimal(amountOfOriginalPrice);
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
