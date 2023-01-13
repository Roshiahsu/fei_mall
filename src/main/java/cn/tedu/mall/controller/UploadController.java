package cn.tedu.mall.controller;

import cn.tedu.mall.pojo.brand.Brand;
import cn.tedu.mall.pojo.product.ProductAddNewDTO;
import cn.tedu.mall.service.IUploadService;
import cn.tedu.mall.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @ClassName UploadController
 * @Version 1.0
 * @Description 上傳測試
 * @Date 2023/1/13、下午3:09
 */
@RestController
@Api(tags = "圖片上傳")
@Slf4j
public class UploadController {

    @Autowired
    private IUploadService uploadService;

    @PostMapping("/upload")
    @ApiOperation("圖片上傳")
    @ApiOperationSupport(order = 100)
    @PreAuthorize("hasRole('admin')")
    public JsonResult insert(MultipartFile picFile,String picture)  {
        log.debug("開始上傳圖片>>>{}");
        uploadService.upload(picFile,picture);
        return JsonResult.ok();
    }

}
