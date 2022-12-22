package cn.tedu.mall.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @ClassName UserRegDTO
 * @Version 1.0
 * @Description TODO
 * @Date 2022/12/21、下午9:22
 */
@Data
public class UserRegDTO implements Serializable {

    @ApiModelProperty(value = "用戶名稱")
    @NotBlank(message = "請輸入正確用戶名")
    private String username;

    @ApiModelProperty(value = "用戶密碼")
    @NotBlank(message = "請輸入正確用戶密碼")
    private String password;
}
