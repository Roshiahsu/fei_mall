package cn.tedu.mall.repository;

import cn.tedu.mall.pojo.order.OrderAddNewDTO;

import java.util.List;

/**
 * @ClassName IOrderRepository
 * @Version 1.0
 * @Description TODO
 * @Date 2023/2/6、上午10:59
 */
public interface IOrderRepository {

        // 存數據
        void addItem(OrderAddNewDTO orderAddNewDTO);

        OrderAddNewDTO getItem(Long userId);

        //刪除數據
        void deleteItem(Long userId);

}
