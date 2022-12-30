package cn.tedu.mall.security;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName LoginPrinciple
 * @Version 1.0
 * @Description TODO
 * @Date 2022/12/29、上午7:36
 */
@Data
public class LoginPrinciple implements Serializable {

    @ApiModelProperty(value = "使用者Id")
    private Long id;

    @ApiModelProperty(value = "使用者名稱")
    private String username;

    @ApiModelProperty(value = "使用者名稱")
    private List<String> authorities;

    public LoginPrinciple(Long id, String username, List<String> authorities) {
        this.id = id;
        this.username = username;
        this.authorities = authorities;
    }
}
