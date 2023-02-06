package cn.tedu.mall.repository.impl;

import cn.tedu.mall.pojo.brand.Brand;
import cn.tedu.mall.pojo.order.OrderAddNewDTO;
import cn.tedu.mall.repository.IOrderRepository;
import cn.tedu.mall.service.IOrderService;
import cn.tedu.mall.utils.ConstUtils;
import cn.tedu.mall.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @ClassName OrderRepository
 * @Version 1.0
 * @Description TODO
 * @Date 2023/2/6、上午11:00
 */
@Repository
public class OrderRepository implements IOrderRepository {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public void addItem(OrderAddNewDTO orderAddNewDTO) {
        Long userId = orderAddNewDTO.getUserId();
        String key = RedisUtils.KEY_PREFIX_ORDER+userId;
        redisTemplate.boundValueOps(key).set(orderAddNewDTO);
    }


    @Override
    public OrderAddNewDTO getItem(Long userId) {
        String key = RedisUtils.KEY_PREFIX_ORDER+userId;
        OrderAddNewDTO orderAddNewDTO=(OrderAddNewDTO)redisTemplate.boundValueOps(key).get();
        return orderAddNewDTO;
    }

    @Override
    public void deleteItem(Long userId) {
        String key = RedisUtils.KEY_PREFIX_ORDER+userId;
        redisTemplate.delete(key);
    }
}
