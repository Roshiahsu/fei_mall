package cn.tedu.mall.pojo.Cart;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ClassName Cart
 * @Version 1.0
 * @Description 寫入資料庫的資料
 * @Date 2022/12/28、下午3:27
 */
@Data
public class Cart implements Serializable {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "用戶id")
    private Long userId;

    @ApiModelProperty(value = "商品編號")
    private Long spuId;

    @ApiModelProperty(value = "加入購物車的價錢")
    private Integer price;

    @ApiModelProperty(value = "購買數量")
    private int quantity;

    @ApiModelProperty(value = "創建時間")
    private LocalDateTime gmtCreate;

    @ApiModelProperty(value = "修改時間")
    private LocalDateTime gmtModified;
}
