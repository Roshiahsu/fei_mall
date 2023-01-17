package cn.tedu.mall.controller;

import cn.tedu.mall.pojo.product.ProductAddNewDTO;
import cn.tedu.mall.pojo.search.ProductForEs;
import cn.tedu.mall.service.ISearchService;
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

/**
 * @ClassName SearchController
 * @Version 1.0
 * @Description 搜尋功能
 * @Date 2023/1/17、下午10:53
 */
@RestController
@Slf4j
@RequestMapping("/search")
@Api(tags = "商品搜尋模組")
public class SearchController {

    @Autowired
    private ISearchService searchService;

    @GetMapping("/")
    @ApiOperation("搜尋商品")
    @ApiOperationSupport(order = 100)
    public JsonResult search(@Param("pageNum") Integer pageNum,
                             @Param("pageSize") Integer pageSize,
                             @Param("keywords") String keywords){
        log.debug("開始搜尋商品");
        JsonPage<ProductForEs> search = searchService.search(keywords, pageNum, pageSize);
        return JsonResult.ok(search);
    }
}
