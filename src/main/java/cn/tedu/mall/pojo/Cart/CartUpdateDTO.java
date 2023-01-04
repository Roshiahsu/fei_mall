package cn.tedu.mall.pojo.Cart;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ClassName CartUpdateDTO
 * @Version 1.0
 * @Description 修改購物車商品DTO
 * @Date 2023/1/4、下午4:37
 */
@Data
public class CartUpdateDTO implements Serializable {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "商品編號")
    private Long spuId;

    @ApiModelProperty(value = "購買數量")
    private int quantity;

    @ApiModelProperty(value = "修改時間")
    private LocalDateTime gmtModified;

    @ApiModelProperty(value = "是否有更改")
    private Integer isUpdate;

}
