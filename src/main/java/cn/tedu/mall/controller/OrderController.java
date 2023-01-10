package cn.tedu.mall.controller;

import cn.tedu.mall.pojo.Cart.CartAddNewDTO;
import cn.tedu.mall.pojo.order.*;
import cn.tedu.mall.service.IOrderService;
import cn.tedu.mall.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName OrderController
 * @Version 1.0
 * @Description 訂單相關Controller層
 * @Date 2023/1/2、上午4:35
 */
@RestController
@Slf4j
@RequestMapping("/order")
@Api(tags = "訂單管理模組")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @PostMapping("/insert")
    @ApiOperation("新增訂單")
    @ApiOperationSupport(order = 100)
    @PreAuthorize("hasRole('user') or hasRole('admin')")
    public JsonResult insert(@RequestBody OrderAddNewDTO orderAddNewDTO){
        log.debug("開始新增訂單Controller");
        log.debug("獲取到的資料>>>{}",orderAddNewDTO);
        OrderAddVO orderInfo = orderService.insert(orderAddNewDTO);
        return JsonResult.ok(orderInfo);
    }

    @GetMapping("/{id}/orderDetail")
    @ApiOperation("訂單詳情")
    @ApiOperationSupport(order = 200)
    @PreAuthorize("hasRole('user') or hasRole('admin')")
    public JsonResult getOrderDetailById(@PathVariable Long id){
        log.debug("開始獲取訂單詳情Controller");
        OrderDetailVO orderDetailVO = orderService.getOrderDetailById(id);
        return JsonResult.ok(orderDetailVO);
    }

    @GetMapping("/{sn}/orderItemList")
    @ApiOperation("購物清單")
    @ApiOperationSupport(order = 250)
    @PreAuthorize("hasRole('user') or hasRole('admin')")
    public JsonResult getOrderDetailById(@PathVariable String sn){
        log.debug("開始獲取購物清單Controller");
        List<OrderItemListVO> orderItemListVOS = orderService.listOrderItem(sn);
        return JsonResult.ok(orderItemListVOS);
    }


    @GetMapping("/list")
    @ApiOperation("訂單列表")
    @ApiOperationSupport(order = 400)
    @PreAuthorize("hasRole('user') or hasRole('admin')")
    public JsonResult listByUserId(){
        log.debug("開始訂單列表Controller");
        List<OrderListVO> orderListVOS = orderService.listByUserId();
        return JsonResult.ok(orderListVOS);
    }
}
