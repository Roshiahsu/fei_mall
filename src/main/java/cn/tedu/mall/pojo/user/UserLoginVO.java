package cn.tedu.mall.pojo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * @ClassName UserLoginVO
 * @Version 1.0
 * @Description 用戶登入時調用loadUserByUsername，回傳的資料
 * @Date 2022/12/21、下午9:22
 */
@Data
public class UserLoginVO implements Serializable {
    @ApiModelProperty(value = "用戶id")
    private Long id;

    @ApiModelProperty(value = "用戶名稱")
    private String username;

    @ApiModelProperty(value = "用戶密碼")
    private String password;

    @ApiModelProperty(value = "是否啟用")
    private Integer isEnable;

    @ApiModelProperty(value = "用戶角色")
    private String roleName;

    //TODO獨立出一個passwordVO
    @ApiModelProperty(value = "用戶電子信箱")
    private String email;

}
