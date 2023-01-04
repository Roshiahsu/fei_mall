package cn.tedu.mall.pojo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @ClassName UserInfoVO
 * @Version 1.0
 * @Description 用戶詳情
 * @Date 2022/12/23、上午10:13
 */
@Data
public class UserInfoVO implements Serializable {
    @ApiModelProperty(value = "用戶id")
    private Long id;

    @ApiModelProperty(value = "用戶名稱")
    private String username;

    @ApiModelProperty(value = "用戶密碼")
    private String password;

    @ApiModelProperty(value = "用戶暱稱")
    private String nickname;

    @ApiModelProperty(value = "用戶電子信箱")
    private String email;

    @ApiModelProperty(value = "用戶手機")
    private String phone;

    @ApiModelProperty(value = "用戶性別")
    private Character sex;

    @ApiModelProperty(value = "用戶性別")
    private String sexName;

    @ApiModelProperty(value = "用戶生日")
    private LocalDate bod;

    @ApiModelProperty(value = "是否啟用")
    private Integer isEnable;

    @ApiModelProperty(value = "紅利積分")
    private Integer rewardPoint;

    @ApiModelProperty(value = "用戶角色ID")
    private Long roleId;

    @ApiModelProperty(value = "用戶角色")
    private String roleName;

    @ApiModelProperty(value = "修改日期")
    private LocalDateTime gmtModified;

    @ApiModelProperty(value = "最後一次登入時間")
    private LocalDateTime gmtLastLogin;

    @ApiModelProperty(value = "郵遞區號")
    private Integer zipCode;

    @ApiModelProperty(value = "縣市")
    private String city;

    @ApiModelProperty(value = "鄉鎮區")
    private String zone;

    @ApiModelProperty(value = "地址詳情")
    private String detailAddress;
}
