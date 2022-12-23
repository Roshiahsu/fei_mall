package cn.tedu.mall.controller;

import cn.tedu.mall.pojo.UserLoginDTO;
import cn.tedu.mall.pojo.UserRegDTO;
import cn.tedu.mall.service.IUserService;
import cn.tedu.mall.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName UserController
 * @Version 1.0
 * @Description 顧客中心管理模組
 * @Date 2022/12/22、上午3:53
 */
@RestController
@Api(tags = "顧客中心管理模組")
@Slf4j
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping("/reg")
    @ApiOperation("用戶註冊")
    @ApiOperationSupport(order = 100)
    public JsonResult reg(@RequestBody UserRegDTO userRegDTO){
        log.debug("註冊功能controller開始");
        userService.reg(userRegDTO);
        return JsonResult.ok();
    }

    @PostMapping("/login")
    @ApiOperation("用戶登入")
    @ApiOperationSupport(order = 200)
    public JsonResult login(@RequestBody UserLoginDTO userLoginDTO){
        log.debug("登入功能controller開始");
        String jwt = userService.login(userLoginDTO);
        return JsonResult.ok(jwt);
    }
}
