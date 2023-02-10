package cn.tedu.mall.controller;

import cn.tedu.mall.service.IPasswordService;
import cn.tedu.mall.web.JsonPage;
import cn.tedu.mall.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName PasswordController
 * @Version 1.0
 * @Description TODO
 * @Date 2023/2/10、下午12:00
 */
@RestController
@Slf4j
@RequestMapping("/password")
@Api(tags = "密碼管理模組")
public class PasswordController {

    @Autowired
    private IPasswordService passwordService;

    @PostMapping("/reset")
    @ApiOperation("重置密碼")
    @ApiOperationSupport(order = 100)
    public JsonResult resetPassword(@RequestParam("username")String username){
        log.debug("開始重置密碼");
        passwordService.resetPassword(username);
        return JsonResult.ok();
    }
}
