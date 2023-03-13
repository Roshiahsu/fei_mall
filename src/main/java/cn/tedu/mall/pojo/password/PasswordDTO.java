package cn.tedu.mall.pojo.password;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName PasswordDTO
 * @Version 1.0
 * @Description TODO
 * @Date 2023/2/10、下午2:03
 */
@Data
public class PasswordDTO implements Serializable {

    @ApiModelProperty(value = "原始密碼")
    private String oldPassword;

    @ApiModelProperty(value = "用戶名稱")
    private String username;

    @ApiModelProperty(value = "新密碼")
    private String newPassword;

}
