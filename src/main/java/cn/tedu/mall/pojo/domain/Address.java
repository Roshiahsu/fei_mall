package cn.tedu.mall.pojo.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName Address
 * @Version 1.0
 * @Description TODO
 * @Date 2023/1/23、下午7:32
 */
@Data
public class Address implements Serializable {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "郵遞區號")
    private String zipCode;

    @ApiModelProperty(value = "縣市")
    private String city;

    @ApiModelProperty(value = "鄉鎮區")
    private String zone;

    @ApiModelProperty(value = "地址詳情")
    private String detailedAddress;

    @ApiModelProperty(value = "標籤")
    private String tag;

    @ApiModelProperty(value = "是否為預設")
    private Integer isDefault;
}
