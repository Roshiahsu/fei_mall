package cn.tedu.mall.controller;

import cn.tedu.mall.mapper.ProductMapper;
import cn.tedu.mall.pojo.product.ProductAddNewDTO;
import cn.tedu.mall.pojo.product.ProductListVO;
import cn.tedu.mall.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName ProductController
 * @Version 1.0
 * @Description TODO
 * @Date 2022/12/26、上午3:01
 */
@RestController
@Slf4j
@Api(tags = "商品中心管理模組")
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductMapper productMapper;

    @PostMapping("/insert")
    @ApiOperation("新增商品")
    @ApiOperationSupport(order = 100)
    public JsonResult insert(@RequestBody ProductAddNewDTO productAddNewDTO){
        log.debug("開始productController.insert");
        productMapper.insert(productAddNewDTO);
        return JsonResult.ok();
    }

    @PostMapping("/delete")
    @ApiOperation("刪除商品")
    @ApiOperationSupport(order = 200)
    public JsonResult delete(Long... ids){
        log.debug("開始productController.insert");
        productMapper.deleteByIds(ids);
        return JsonResult.ok();
    }
    @GetMapping("/{typeId}/listProduct")
    @ApiOperation("商品列表")
    @ApiOperationSupport(order = 250)
    public JsonResult listProduct(@PathVariable Long typeId){
        log.debug("開始productController.listProduct");
        List<ProductListVO> vos = productMapper.listProduct(typeId);
        return JsonResult.ok(vos);
    }

}
