package cn.tedu.mall.pojo.user;

import cn.tedu.mall.utils.Regex;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @ClassName UserRegDTO
 * @Version 1.0
 * @Description 用戶快速註冊請求資料
 * @Date 2022/12/21、下午9:22
 */
@Data
public class UserRegDTO implements Serializable, Regex {

    @ApiModelProperty(value = "用戶名稱")
    @NotBlank(message = "請輸入正確用戶名")
    private String username;

    @ApiModelProperty(value = "用戶密碼")
    @NotBlank(message = "請輸入正確用戶密碼")
    private String password;

    @ApiModelProperty(value = "用戶電子信箱")
    @Pattern(regexp = REGEXP_USER_EMAIL ,message = MESSAGE_USER_EMAIL)
    private String email;
}
