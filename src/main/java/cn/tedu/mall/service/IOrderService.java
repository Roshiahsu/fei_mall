package cn.tedu.mall.service;

import cn.tedu.mall.pojo.order.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName IOrderService
 * @Version 1.0
 * @Description TODO
 * @Date 2023/1/2、上午2:48
 */
@Transactional
public interface IOrderService {

    OrderAddVO insert(OrderAddNewDTO orderAddNewDTO);

    List<OrderListVO> listByUserId();

    OrderDetailVO getOrderDetailById(Long id);

    List<OrderItemListVO> listOrderItem(String sn);

}
