package cn.tedu.mall.controller;

import cn.tedu.mall.pojo.product.ProductAddNewDTO;
import cn.tedu.mall.pojo.product.ProductVO;
import cn.tedu.mall.pojo.product.ProductTypeListVO;
import cn.tedu.mall.pojo.product.ProductUpdateDTO;
import cn.tedu.mall.service.IProductService;
import cn.tedu.mall.web.JsonPage;
import cn.tedu.mall.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName ProductController
 * @Version 1.0
 * @Description 商品相關Controller
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
    @PreAuthorize("hasRole('admin')")
    public JsonResult insert(@RequestBody ProductAddNewDTO productAddNewDTO){
        log.debug("開始新增商品");
        productService.insert(productAddNewDTO);
        return JsonResult.ok();
    }

    @GetMapping("/{id}/delete")
    @ApiOperation("刪除商品")
    @ApiOperationSupport(order = 200)
    @PreAuthorize("hasRole('admin')")
    public JsonResult delete(@PathVariable("id") Long... ids){
        log.debug("開始刪除商品");
        productService.deleteByIds(ids);
        return JsonResult.ok();
    }

    @GetMapping("/{typeId}/listProduct")
    @ApiOperation("商品列表")
    @ApiOperationSupport(order = 300)
    public JsonResult listProduct(@Param ("pageNum") Integer pageNum,
                                  @Param("pageSize") Integer pageSize,
                                  @PathVariable Integer typeId){
        log.debug("開始查詢商品列表");
        JsonPage<ProductVO> list = productService.listProduct(pageNum, pageSize, typeId);
        return JsonResult.ok(list);
    }

    @GetMapping("/{id}/details")
    @ApiOperation("商品詳情")
    @ApiOperationSupport(order = 350)
    public JsonResult getProductById(@PathVariable Long id){
        log.debug("開始查詢商品詳情");
        ProductVO vo = productService.getById(id);
        return JsonResult.ok(vo);
    }

    @GetMapping("/productTypeList")
    @ApiOperation("推播列表")
    @ApiOperationSupport(order = 360)
    public JsonResult listProductTypeList(){
        log.debug("開始獲取推播列表");
        List<ProductTypeListVO> listVOS = productService.listProductType();
        return JsonResult.ok(listVOS);
    }

    @PostMapping("/update")
    @ApiOperation("修改商品詳情")
    @ApiOperationSupport(order = 400)
    @PreAuthorize("hasRole('admin')")
    public JsonResult updateProductInfo(@RequestBody ProductUpdateDTO productUpdateDTO){
        log.debug("開始修改商品");
        productService.updateById(productUpdateDTO);
        return JsonResult.ok();
    }
}
