package cn.tedu.mall.mapper;

import cn.tedu.mall.pojo.Cart.Cart;
import cn.tedu.mall.pojo.order.Order;
import cn.tedu.mall.pojo.order.OrderItemAddNewDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName OrderMapper
 * @Version 1.0
 * @Description TODO
 * @Date 2023/1/2、上午2:29
 */
@Repository
public interface OrderMapper {

    /**
     * 新增訂單
     * @param order
     * @return
     */
    int insert(Order order);

    /**
     * 新增訂單中包含的商品資料
     * @param orderItemAddNewDTOS
     * @return
     */
    int insertOrderItems(List<OrderItemAddNewDTO> orderItemAddNewDTOS);


}
