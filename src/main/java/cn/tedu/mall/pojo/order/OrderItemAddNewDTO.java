package cn.tedu.mall.pojo.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @ClassName OrderItemAddNewDTO
 * @Version 1.0
 * @Description TODO
 * @Date 2023/1/2、上午3:42
 */
@Data
public class OrderItemAddNewDTO implements Serializable {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "訂單編號")
    private String sn;

    @ApiModelProperty(value = "商品id")
    @NotBlank(message = "商品id不得為空！！")
    private Long spuId;
}
