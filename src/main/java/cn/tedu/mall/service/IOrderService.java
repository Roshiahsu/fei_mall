package cn.tedu.mall.service;

import cn.tedu.mall.pojo.order.*;
import cn.tedu.mall.pojo.product.ProductVO;
import cn.tedu.mall.web.JsonPage;
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

    /**
     * 新增訂單
     * @param orderAddNewDTO
     * @return
     */
    OrderAddVO insert(OrderAddNewDTO orderAddNewDTO);

    /**
     * 根據用戶id獲取訂單編號
     * @return
     */
    List<OrderListVO> listByUserId();

    /**
     * 管理員獲取全部訂單
     * @return
     */
    JsonPage<OrderListVO> listForAdmin(OrderQueryDTO orderQueryDTO);

    /**
     * 獲取訂單詳情
     * @param id 商品id
     * @return
     */
    OrderDetailVO getOrderDetailById(Long id);

    /**
     * 根據訂單編號獲購物清單
     * @param sn 訂單編號
     * @return
     */
    List<OrderItemListVO> listOrderItem(String sn);

}
