package cn.tedu.mall.service;

import cn.tedu.mall.pojo.order.OrderAddNewDTO;
import cn.tedu.mall.pojo.order.OrderAddVO;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName IOrderService
 * @Version 1.0
 * @Description TODO
 * @Date 2023/1/2、上午2:48
 */
@Transactional
public interface IOrderService {

    OrderAddVO insert(OrderAddNewDTO orderAddNewDTO);
}
