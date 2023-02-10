package cn.tedu.mall.controller;

import cn.tedu.mall.pojo.password.PasswordDTO;
import cn.tedu.mall.pojo.user.UserUpdateDTO;
import cn.tedu.mall.service.IPasswordService;
import cn.tedu.mall.service.impl.PasswordServiceImpl;
import cn.tedu.mall.web.JsonPage;
import cn.tedu.mall.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("/matches")
    @ApiOperation("密碼驗證")
    @ApiOperationSupport(order = 100)
    public JsonResult matchesPassword(@RequestBody PasswordDTO passwordDTO){
        log.debug("開始驗證密碼");
        passwordService.matchesPassword(passwordDTO);
        return JsonResult.ok();
    }

    @Deprecated
    @PostMapping("/update")
    @ApiOperation("修改密碼")
    @ApiOperationSupport(order = 200)
    public JsonResult updatePassword(@RequestBody PasswordDTO PasswordDTO){
        log.debug("開始驗證密碼");
        return JsonResult.ok();
    }


    @PostMapping("/reset")
    @ApiOperation("重置密碼")
    @ApiOperationSupport(order = 300)
    public JsonResult resetPassword(@RequestBody PasswordDTO passwordDTO){
        log.debug("開始重置密碼");
        passwordService.resetPassword(passwordDTO.getUsername());
        return JsonResult.ok();
    }
}
