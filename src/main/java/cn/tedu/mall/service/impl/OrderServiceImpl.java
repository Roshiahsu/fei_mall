package cn.tedu.mall.service.impl;

import cn.tedu.mall.exception.ServiceException;
import cn.tedu.mall.mapper.CartMapper;
import cn.tedu.mall.mapper.OrderMapper;
import cn.tedu.mall.mapper.ProductMapper;
import cn.tedu.mall.mapper.RecipientMapper;
import cn.tedu.mall.pojo.domain.Recipient;
import cn.tedu.mall.pojo.order.*;
import cn.tedu.mall.pojo.product.ProductUpdateDTO;
import cn.tedu.mall.service.IOrderService;
import cn.tedu.mall.utils.ConstUtils;
import cn.tedu.mall.web.ServiceCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @ClassName OrderServiceImpl
 * @Version 1.0
 * @Description 訂單相關Service層
 * @Date 2023/1/2、上午2:48
 */
@Service
@Slf4j
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private RecipientMapper recipientMapper;

    /**
     * 設定訂單狀態，狀態2為訂單成立
     */
    public static final Integer ORDER_STATUS_SUCCESS = 2;

    /**
     * 新增訂單
     * @param orderAddNewDTO
     * @return
     */
    @Override
    public OrderAddVO insert(OrderAddNewDTO orderAddNewDTO) {
        log.debug("開始新增訂單");
        log.debug("獲取到的DTO>>>{}",orderAddNewDTO);
        //從上下文獲取id
        Long userId = ConstUtils.getUserId();

        Order order = new Order();

        //初始化收件人資料
        Recipient recipient = new Recipient();
        recipient.setRecipientName(orderAddNewDTO.getRecipientName());
        recipient.setRecipientPhone(orderAddNewDTO.getRecipientPhone());
        recipient.setRecipientAddress(orderAddNewDTO.getRecipientAddress());
        Long recipientId = recipientMapper.getRecipientId(recipient);
        log.debug("獲取到的id>>>{}",recipientId);
        if (recipientId !=null){
            //資料已存在，直接設定
            log.debug("資料已存在，直接設定,recipientId=={}",recipientId);
            //設定收件人ID
            order.setRecipientId(recipientId);
        }else{
            //資料不存在，新增資料
            log.debug("資料不存在，新增資料");
            int rows = recipientMapper.insert(recipient);
            order.setRecipientId(recipient.getId());
            if (rows != 1){
                throw new ServiceException(ServiceCode.ERR_INSERT,"伺服器忙碌中，請稍後再試!!");
            }
        }

        //初始化要寫入oms_order表中的訂單資料

        BeanUtils.copyProperties(orderAddNewDTO,order);
        order.setUserId(userId);
        order.setOrderStatus(ORDER_STATUS_SUCCESS);

        //數據加載
        loadOrder(order);
        log.debug("準備寫入資料庫的Order>>>{}",order);

        //將訂單寫入資料庫
        int rows= orderMapper.insert(order);
        if (rows != 1){
            throw new ServiceException(ServiceCode.ERR_INSERT,"伺服器忙碌中，請稍後再試!!");
        }
        //宣告準備寫入購物列表的List
        List<OrderItemAddNewDTO> orderItems = new ArrayList<>();

        //獲取訂單中的商品訊息
        for (OrderItemAddNewDTO orderItem : orderAddNewDTO.getOrderItems()) {
            //外鍵，將omsOrderItem與omsOrder透過sn進行關聯
            orderItem.setSn(order.getSn());
            orderItems.add(orderItem);

            //透過spuId修改商品庫存
            ProductUpdateDTO productUpdateDTO = new ProductUpdateDTO();
            productUpdateDTO.setId(orderItem.getSpuId());
            //獲取庫存
            int stock = orderItem.getStock();
            //將庫存減少購買數量
            productUpdateDTO.setStock(stock-orderItem.getQuantity());
            log.debug("準備傳入資料庫的資料{}",productUpdateDTO);
            rows = productMapper.updateById(productUpdateDTO);
            if (rows == 0){
                throw new ServiceException(ServiceCode.ERR_UPDATE,"伺服器忙碌中，請稍後再試!!");
            }
        }
        log.debug("獲取到的orderItems>>>{}",orderItems);
        //orderItems是商品
        //order是訂單
        orderMapper.insertOrderItems(orderItems);

        //清空購物車
        log.debug("清空購物車");
        rows = cartMapper.deleteAllCarts(userId);
        if (rows == 0){
            throw new ServiceException(ServiceCode.ERR_DELETE,"伺服器忙碌中，請稍後再試!!");
        }

        OrderAddVO orderAddVO = new OrderAddVO();
        //訂單id
        orderAddVO.setId(order.getId());
        //訂單編號
        orderAddVO.setSn(order.getSn());
        //實際支付金額
        orderAddVO.setPayAmount(order.getAmountOfActualPay());

        return orderAddVO;
    }

    /**
     * 透過userId獲取訂單列表
     * @return
     */
    @Override
    public List<OrderListVO> listByUserId() {
        log.debug("開使獲取訂單列表");
        //從上下文獲取id
        Long userId = ConstUtils.getUserId();
        List<OrderListVO> orderListVOS = orderMapper.listOrdersByUserId(userId);
        return orderListVOS;
    }

    /**
     * 根據id獲取訂單詳情
     * @param id
     * @return
     */
    @Override
    public OrderDetailVO getOrderDetailById(Long id) {
        log.debug("開始獲取訂單詳情");
        OrderDetailVO orderDetailVO = orderMapper.getOrderDetailById(id);
        return orderDetailVO;
    }

    /**
     * 根據訂單編號查詢購物清單
     * @param sn 訂單編號
     * @return
     */
    @Override
    public List<OrderItemListVO> listOrderItem(String sn) {
        log.debug("開始查詢訂單內的購物清單");
        List<OrderItemListVO> orderItemListVOS = orderMapper.listOrderItem(sn);
        return orderItemListVOS;
    }

    /**
     * 數據加載
     * @param order
     */
    private void loadOrder(Order order) {

        //判斷sn 訂單編號
        if(order.getSn()==null){
            //使用UUID生成訂單編號
            order.setSn(createOrderNumber());
        }
        //判斷運費
        if(order.getAmountOfFreight()==null){
            //使用BigInteger做精確計算
            order.setAmountOfFreight(new BigInteger("0"));
        }
        //判斷運費
        if(order.getAmountOfDiscount()==null){

            order.setAmountOfDiscount(new BigInteger("0"));
        }
        //判斷創建時間
        if(order.getGmtCreate()==null){
            LocalDateTime now = LocalDateTime.now();
            order.setGmtCreate(now);
        }
        //判斷原價
        if(order.getAmountOfOriginalPrice()==null){
            //原價必須根據實際情況獲取，所以不賦值，直接拋異常
            throw new ServiceException(ServiceCode.ERR_BAD_REQUEST,"沒有訂單原價！！");
        }
        //計算實際支付金額
        BigInteger amountOfFreight = order.getAmountOfFreight(); //運費
        BigInteger amountOfDiscount = order.getAmountOfDiscount();//折扣
        BigInteger amountOfOriginalPrice = order.getAmountOfOriginalPrice();//原價
        BigInteger actualPay = amountOfOriginalPrice.add(amountOfFreight).subtract(amountOfDiscount);
        order.setAmountOfActualPay(actualPay); //實際需要支付的金額
    }

    /**
     * 生成訂單編號(日期8位+時間戳5位+4位隨機數)
     * @return
     */
    public String createOrderNumber(){
        //格式化日期為"yyyymmdd"
        DateFormat format = new SimpleDateFormat("yyMMdd");
        Date date = new Date();

        StringBuffer buffer = new StringBuffer();
        //加入日期
        buffer.append(format.format(date));
        //加入時間戳5位
        buffer.append((date.getTime()+"").substring(5));
        //獲取4為隨機數
        buffer.append(getRandNum(4));

        return buffer.toString();
    }

    /**
     * 獲取N為隨機數
     * @return
     */
    public String getRandNum(int length){
        Random random = new Random();
        StringBuffer result = new StringBuffer();

        for (int i = 0; i < length; i++) {
            result.append(random.nextInt(10));
        }
        if(result.length()>0){
            return result.toString();
        }
        return null;
    }


}
