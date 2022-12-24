package cn.tedu.mall.pojo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @ClassName UserLoginDTO
 * @Version 1.0
 * @Description TODO
 * @Date 2022/12/22、下午4:08
 */
@Data
public class UserLoginDTO implements Serializable {

    @ApiModelProperty(value = "用戶名稱")
    @NotBlank(message = "請輸入正確用戶名")
    private String username;

    @ApiModelProperty(value = "用戶密碼")
    @NotBlank(message = "請輸入正確用戶密碼")
    private String password;
}
