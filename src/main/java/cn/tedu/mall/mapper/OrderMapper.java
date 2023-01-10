package cn.tedu.mall.mapper;

import cn.tedu.mall.pojo.Cart.Cart;
import cn.tedu.mall.pojo.order.*;
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

    /**
     * 根據用戶id查詢訂單列表
     * @param userId
     * @return
     */
    List<OrderListVO> listOrdersByUserId(Long userId);

    /**
     * 根據id查詢訂單詳情
     * @param id oms_order.id
     * @return
     */
    OrderDetailVO getOrderDetailById(Long id);

    /**
     * 根據訂單編號查詢購買清單
     * @param sn 訂單編號
     * @return
     */
    List<OrderItemListVO> listOrderItem(String sn);

}
