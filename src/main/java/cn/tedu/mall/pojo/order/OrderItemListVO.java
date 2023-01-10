package cn.tedu.mall.pojo.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @ClassName OrderItemListVO
 * @Version 1.0
 * @Description 訂單詳情內的購物清單
 * @Date 2023/1/10、下午6:39
 */
@Data
public class OrderItemListVO implements Serializable {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "商品名稱")
    private String productName;

    @ApiModelProperty(value = "購買數量")
    private Integer quantity;

    @ApiModelProperty(value = "加入購物車的價錢")
    private Integer price;

    @ApiModelProperty(value = "小計")
    private Integer subtotal;

}
