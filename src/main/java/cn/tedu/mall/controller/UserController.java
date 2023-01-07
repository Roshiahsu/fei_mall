package cn.tedu.mall.controller;

import cn.tedu.mall.pojo.user.UserInfoVO;
import cn.tedu.mall.pojo.user.UserLoginDTO;
import cn.tedu.mall.pojo.user.UserRegDTO;
import cn.tedu.mall.pojo.user.UserUpdateDTO;
import cn.tedu.mall.service.IUserService;
import cn.tedu.mall.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName UserController
 * @Version 1.0
 * @Description 顧客中心管理模組
 * @Date 2022/12/22、上午3:53
 */
@RestController
@Api(tags = "顧客中心管理模組")
@Slf4j
@RequestMapping("/user")
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

    @PostMapping("/update")
    @ApiOperation("詳情修改")
    @ApiOperationSupport(order = 300)
    public JsonResult update(@RequestBody UserUpdateDTO userUpdateDTO){
        log.debug("修改功能controller開始");
        userService.update(userUpdateDTO);
        return JsonResult.ok();
    }

    @GetMapping("/userInfo")
    @ApiOperation("用戶詳情")
    @ApiOperationSupport(order = 350)
    @PreAuthorize("hasRole('user') or hasRole('admin')")
    public JsonResult userInfo(){
        log.debug("用戶詳情controller開始");
        UserInfoVO userInfoVO = userService.userInfo();
        return JsonResult.ok(userInfoVO);
    }
}
