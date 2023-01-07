package cn.tedu.mall;

import cn.tedu.mall.mapper.OrderMapper;

import cn.tedu.mall.pojo.order.Order;
import cn.tedu.mall.pojo.order.OrderDetailVO;
import cn.tedu.mall.pojo.order.OrderListVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * @ClassName OrderTest
 * @Version 1.0
 * @Description TODO
 * @Date 2023/1/2、上午2:39
 */
@SpringBootTest
@Slf4j
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

    @Test
    public void listOrdersByUserId(){
        List<OrderListVO> orderListVOS = orderMapper.listOrdersByUserId(11L);
        for (OrderListVO orderListVO : orderListVOS) {
            log.debug("查詢到的資料>>>{}",orderListVO);

        }
    }

    private LocalDate now;
    @Test
    public void dateTest(){

        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        now = LocalDate.now();
        String test ;
        test = format.format(date)+"1234";
        log.debug("獲取到的時間>>{}",test);
        log.debug("時間戳>>>{}",date.getTime());
    }


    @Test
    public void getOrderDetail(){
        OrderDetailVO orderDetailById = orderMapper.getOrderDetailById(61L);

        log.debug("獲取到的資料>>>{}",orderDetailById);
    }
}
