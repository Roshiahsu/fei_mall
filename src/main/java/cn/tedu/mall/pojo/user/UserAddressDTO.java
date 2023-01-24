package cn.tedu.mall.pojo.user;

import cn.tedu.mall.utils.ConstUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @ClassName UserAddressDTO
 * @Version 1.0
 * @Description 新增、修改用戶地址DTO
 * @Date 2023/1/13、上午11:16
 */
@Data
public class UserAddressDTO implements Serializable {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "用戶id")
    private Long userId;

    @ApiModelProperty(value = "郵遞區號")
    private Integer zipCode;

    @ApiModelProperty(value = "縣市")
    private String city;

    @ApiModelProperty(value = "鄉鎮區")
    private String zone;

    @NotBlank(message = "地址詳情不得為空")
    @ApiModelProperty(value = "地址詳情")
    private String detailedAddress;

    @ApiModelProperty(value = "標籤")
    private String tag;

    @ApiModelProperty(value = "是否啟用")
    private  Integer isEnable;

    @ApiModelProperty(value = "是否為預設")
    private  Integer isDefault;

    public UserAddressDTO() {
        //預設啟用
        this.isEnable = ConstUtils.IS_ENABLE;
    }
}
