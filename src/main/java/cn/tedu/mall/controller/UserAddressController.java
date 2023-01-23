package cn.tedu.mall.controller;

import cn.tedu.mall.pojo.domain.Address;
import cn.tedu.mall.pojo.user.UserAddressDTO;
import cn.tedu.mall.service.IUserAddressService;
import cn.tedu.mall.utils.ConstUtils;
import cn.tedu.mall.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @ClassName UserAddressController
 * @Version 1.0
 * @Description TODO
 * @Date 2023/1/23、下午7:54
 */

@RestController
@Api(tags = "顧客地址管理模組")
@Slf4j
@RequestMapping("/address")
public class UserAddressController {

    @Autowired
    private IUserAddressService userAddressService;

    @PostMapping("/addressInfo")
    @ApiOperation("地址詳情")
    @ApiOperationSupport(order = 100)
    @PreAuthorize("hasRole('user') or hasRole('admin')")
    public JsonResult getAddressInfo(@RequestBody @Valid UserAddressDTO userAddressDTO){
        log.debug("查詢地址controller開始");
        Address addressById = userAddressService.getAddressById(userAddressDTO.getId());
        return JsonResult.ok(addressById);
    }

    @GetMapping("/addressList")
    @ApiOperation("地址詳情列表")
    @ApiOperationSupport(order = 150)
    @PreAuthorize("hasRole('user') or hasRole('admin')")
    public JsonResult listAddressInfo(){
        log.debug("查詢地址列表controller開始");
        Long userId = ConstUtils.getUserId();
        List<Address> listAddressVO= userAddressService.listAddress(userId);
        return JsonResult.ok(listAddressVO);
    }

    @PostMapping("/updateAddress")
    @ApiOperation("修改地址")
    @ApiOperationSupport(order = 300)
    @PreAuthorize("hasRole('user') or hasRole('admin')")
    public JsonResult update(@RequestBody @Valid UserAddressDTO userAddressDTO){
        log.debug("修改地址controller開始");
        userAddressService.updateAddress(userAddressDTO);
        return JsonResult.ok();
    }
}
