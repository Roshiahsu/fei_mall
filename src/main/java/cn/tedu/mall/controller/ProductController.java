package cn.tedu.mall.controller;

import cn.tedu.mall.mapper.ProductMapper;
import cn.tedu.mall.pojo.product.ProductAddNewDTO;
import cn.tedu.mall.pojo.product.ProductListVO;
import cn.tedu.mall.service.IProductService;
import cn.tedu.mall.web.JsonPage;
import cn.tedu.mall.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
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
    private IProductService productService;

    @PostMapping("/insert")
    @ApiOperation("新增商品")
    @ApiOperationSupport(order = 100)
    public JsonResult insert(@RequestBody ProductAddNewDTO productAddNewDTO){
        log.debug("開始productController.insert");
        productService.insert(productAddNewDTO);
        return JsonResult.ok();
    }

    @PostMapping("/delete")
    @ApiOperation("刪除商品")
    @ApiOperationSupport(order = 200)
    public JsonResult delete(Long... ids){
        log.debug("開始productController.delete");
        productService.deleteByIds(ids);
        return JsonResult.ok();
    }
    @GetMapping("/{typeId}/listProduct")
    @ApiOperation("商品列表")
    @ApiOperationSupport(order = 300)
    public JsonResult listProduct(@Param ("pageNum") Integer pageNum,@Param("pageSize") Integer pageSize,@PathVariable Long typeId){
        log.debug("開始productController.listProduct");
        JsonPage<ProductListVO> list = productService.listProduct(pageNum, pageSize, typeId);
        return JsonResult.ok(list);
    }

    @GetMapping("/{id}/details")
    @ApiOperation("商品詳情")
    @ApiOperationSupport(order = 350)
    public JsonResult getProductById(@PathVariable Long id){
        log.debug("開始productController.getProductById");
        ProductListVO vo = productService.getById(id);
        return JsonResult.ok(vo);
    }

}
