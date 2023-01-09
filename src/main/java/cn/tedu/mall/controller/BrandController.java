package cn.tedu.mall.controller;

import cn.tedu.mall.pojo.Cart.CartAddNewDTO;
import cn.tedu.mall.pojo.brand.Brand;
import cn.tedu.mall.service.IBrandService;
import cn.tedu.mall.web.JsonPage;
import cn.tedu.mall.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName BrandController
 * @Version 1.0
 * @Description 品牌管理模組
 * @Date 2023/1/9、下午9:55
 */
@RestController
@RequestMapping("/brands")
@Api(tags = "品牌管理模組")
@Slf4j
//@PreAuthorize("hasRole('admin')")
public class BrandController {

    @Autowired
    private IBrandService brandService;

    @PostMapping("/insert")
    @ApiOperation("新增品牌")
    @ApiOperationSupport(order = 100)
    public JsonResult insert(@RequestBody Brand Brand){
        log.debug("開始新增品牌Controller");
        log.debug("獲取到的資料>>>{}",Brand);
        brandService.insert(Brand);
        return JsonResult.ok();
    }

    @GetMapping("/list")
    @ApiOperation("品牌列表")
    @ApiOperationSupport(order = 400)
    public JsonResult list(@Param("pageNum") Integer pageNum){
        log.debug("開始品牌列表Controller");
        JsonPage<Brand> jsonPage = brandService.listBrand(pageNum);
        return JsonResult.ok(jsonPage);
    }
}
