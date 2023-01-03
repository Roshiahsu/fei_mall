package cn.tedu.mall;

import cn.tedu.mall.mapper.OrderMapper;

import cn.tedu.mall.pojo.order.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @ClassName OrderTest
 * @Version 1.0
 * @Description TODO
 * @Date 2023/1/2、上午2:39
 */
@SpringBootTest
public class OrderTest {

    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void inserTest(){
        Order order = new Order();
        order.setUserId(11L);
        order.setSn("2023123123123");
        order.setRewardPoint(10);

        orderMapper.insert(order);

    }
}
