package cn.tedu.mall.pojo.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @ClassName UserUpdateDTO
 * @Version 1.0
 * @Description 修改用戶資料請求資料
 * @Date 2022/12/23、下午1:24
 */
@Data
public class UserUpdateDTO implements Serializable {

    @ApiModelProperty(value = "用戶id")
    @NotBlank(message = "無效的Id")
    private Long id;

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

    @ApiModelProperty(value = "用戶生日")
    private LocalDate bod;

    @ApiModelProperty(value = "是否啟用")
    private Integer isEnable;

    @ApiModelProperty(value = "紅利積分")
    private Integer rewardPoint;

    @ApiModelProperty(value = "用戶角色")
    private Integer roleId;

    public UserUpdateDTO() {
        this.isEnable = 1;
    }
}
