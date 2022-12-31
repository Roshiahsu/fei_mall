package cn.tedu.mall.pojo.Cart;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @ClassName CartAddNewDTO
 * @Version 1.0
 * @Description TODO
 * @Date 2022/12/28、下午3:23
 */
@Data
public class CartAddNewDTO implements Serializable {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "用戶id")
    private Long userId;

    @ApiModelProperty(value = "商品id")
    @NotBlank(message = "請輸入正確的商品id。")
    private Long spuId;

    @ApiModelProperty(value = "加入購物車的價錢")
    @NotBlank(message = "請輸入正確的價錢。")
    private Integer price;

    @ApiModelProperty(value = "購買數量")
    @NotBlank(message = "請輸入正確的購買數量。")
    private int quantity;
}
