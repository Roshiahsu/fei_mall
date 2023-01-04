package cn.tedu.mall.controller;

import cn.tedu.mall.pojo.Cart.CartAddNewDTO;
import cn.tedu.mall.pojo.order.OrderAddNewDTO;
import cn.tedu.mall.pojo.order.OrderAddVO;
import cn.tedu.mall.service.IOrderService;
import cn.tedu.mall.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName OrderController
 * @Version 1.0
 * @Description 訂單相關Controller層
 * @Date 2023/1/2、上午4:35
 */
@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @PostMapping("/insert")
    @ApiOperation("新增訂單")
    @ApiOperationSupport(order = 100)
    public JsonResult insert(@RequestBody OrderAddNewDTO orderAddNewDTO){
        log.debug("開始新增訂單Controller");
        log.debug("獲取到的資料>>>{}",orderAddNewDTO);
        OrderAddVO orderInfo = orderService.insert(orderAddNewDTO);
        return JsonResult.ok(orderInfo);
    }
}
