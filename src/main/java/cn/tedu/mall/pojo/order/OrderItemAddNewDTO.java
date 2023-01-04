package cn.tedu.mall.pojo.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @ClassName OrderItemAddNewDTO
 * @Version 1.0
 * @Description 新增訂單包含的商品訊息
 * @Date 2023/1/2、上午3:42
 */
@Data
public class OrderItemAddNewDTO implements Serializable {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "訂單編號")
    private String sn;

    @ApiModelProperty(value = "商品id")
    private Long spuId;

    @ApiModelProperty(value = "購買數量")
    private int quantity;

    @ApiModelProperty(value = "庫存")
    private int stock;
}
