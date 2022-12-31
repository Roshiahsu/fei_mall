package cn.tedu.mall.controller;

import cn.tedu.mall.pojo.Cart.CartAddNewDTO;
import cn.tedu.mall.pojo.Cart.CartInfoVO;
import cn.tedu.mall.pojo.product.ProductAddNewDTO;
import cn.tedu.mall.service.ICartService;
import cn.tedu.mall.web.JsonPage;
import cn.tedu.mall.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName CartController
 * @Version 1.0
 * @Description TODO
 * @Date 2022/12/30、下午7:36
 */
@RestController
@Slf4j
@Api(tags = "購物車管理模組")
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ICartService cartService;

    @PostMapping("/insert")
    @ApiOperation("新增購物車")
    @ApiOperationSupport(order = 100)
    public JsonResult insert(@RequestBody CartAddNewDTO cartAddNewDTO){
        log.debug("開始新增購物車Controller");
        log.debug("獲取到的資料>>>{}",cartAddNewDTO);
        cartService.insert(cartAddNewDTO);
        return JsonResult.ok();
    }

    @GetMapping("/list")
    @ApiOperation("查詢用戶購物車列表")
    @ApiOperationSupport(order = 400)
    public JsonResult listCartByUserId(@Param("pageNum")Integer pageNum,@Param("pageSize")Integer pageSize){
        log.debug("查詢用戶購物車列表Controller");
        JsonPage<CartInfoVO> cartInfoVOJsonPage = cartService.listCartByUserId(pageNum, pageSize);
        return JsonResult.ok(cartInfoVOJsonPage);
    }

}
