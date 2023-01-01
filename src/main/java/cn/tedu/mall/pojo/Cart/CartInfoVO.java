package cn.tedu.mall.pojo.Cart;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ClassName CartInfoVO
 * @Version 1.0
 * @Description TODO
 * @Date 2022/12/30、下午8:21
 */
@Data
public class CartInfoVO implements Serializable {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "用戶id")
    private Long userId;

    @ApiModelProperty(value = "商品編號")
    private Long spuId;

    @ApiModelProperty(value = "商品名稱")
    private String productName;

    @ApiModelProperty(value = "加入購物車的價錢")
    private Integer price;

    @ApiModelProperty(value = "購買數量")
    private int quantity;

    @ApiModelProperty(value = "小計")
    private int subtotal;

}
