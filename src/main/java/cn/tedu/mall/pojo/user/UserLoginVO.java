package cn.tedu.mall.pojo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;


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

}
