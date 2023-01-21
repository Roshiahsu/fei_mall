package cn.tedu.mall.controller;

import cn.tedu.mall.service.IKeywordService;
import cn.tedu.mall.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName KeywordController
 * @Version 1.0
 * @Description TODO 關鍵字
 * @Date 2023/1/19、下午5:52
 */
@RestController
@Slf4j
@Api(tags = "關鍵字管理模組")
@RequestMapping("/keyword")
public class KeywordController {

    @Autowired
    private IKeywordService keywordService;

    @GetMapping("/")
    @ApiOperation("獲取關鍵字列表")
    @ApiOperationSupport(order = 100)
    public JsonResult listKeywords(){
        log.debug("開始獲取關鍵字Controller");
        List<String> keywordVO = keywordService.listKeyword();
        return JsonResult.ok(keywordVO);
    }
}
