package cn.tedu.mall.service.impl;

import cn.tedu.mall.exception.ServiceException;
import cn.tedu.mall.mapper.CartMapper;
import cn.tedu.mall.mapper.OrderMapper;
import cn.tedu.mall.mapper.ProductMapper;
import cn.tedu.mall.pojo.order.Order;
import cn.tedu.mall.pojo.order.OrderAddNewDTO;
import cn.tedu.mall.pojo.order.OrderAddVO;
import cn.tedu.mall.pojo.order.OrderItemAddNewDTO;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName OrderServiceImpl
 * @Version 1.0
 * @Description TODO
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

    @Override
    public OrderAddVO insert(OrderAddNewDTO orderAddNewDTO) {
        log.debug("開始新增訂單");
        log.debug("獲取到的DTO>>>{}",orderAddNewDTO);
        //從上下文獲取id
        Long userId = ConstUtils.getUserId();

        //初始化要寫入oms_order表中的訂單資料
        Order order = new Order();
        BeanUtils.copyProperties(orderAddNewDTO,order);
        order.setUserId(userId);
        //數據加載
        loadOrder(order);
        log.debug("獲取到的Order>>>{}",order);

        //將訂單寫入資料庫
        int rows= orderMapper.insert(order);
        if (rows != 1){
            throw new ServiceException(ServiceCode.ERR_INSERT,"伺服器忙碌中，請稍後再試!!");
        }
        //獲取訂單中的商品訊息
        List<OrderItemAddNewDTO> orderItems = new ArrayList<>();
        for (OrderItemAddNewDTO orderItem : orderAddNewDTO.getOrderItems()) {
            //外鍵，將omsOrderItem與omsOrder透過sn進行關聯
            orderItem.setSn(order.getSn());
            orderItems.add(orderItem);

            //修改商品庫存數量
            ProductUpdateDTO productUpdateDTO = new ProductUpdateDTO();
            productUpdateDTO.setId(orderItem.getSpuId());
            //獲取庫存
            int stock = orderItem.getStock();
            //減少庫存
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

    //加載數據
    private void loadOrder(Order order) {

        //判斷sn 訂單編號
        if(order.getSn()==null){
            //使用UUID生成訂單編號
            order.setSn(UUID.randomUUID().toString());
        }
        //判斷運費
        if(order.getAmountOfFreight()==null){
            //需要精確計算必須使用BigDecimal
            order.setAmountOfFreight(new BigInteger("0"));
        }
        //判斷運費
        if(order.getAmountOfDiscount()==null){
            //需要精確計算必須使用BigDecimal
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
}
