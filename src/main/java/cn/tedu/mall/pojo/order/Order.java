package cn.tedu.mall.pojo.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 * @ClassName Order
 * @Version 1.0
 * @Description 寫入資料庫的資料
 * @Date 2023/1/2、上午2:29
 */
@Data
public class Order implements Serializable {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "訂單編號")
    private String sn;

    @ApiModelProperty(value = "用戶id")
    private Long userId;

    @ApiModelProperty(value = "積分")
    private Integer rewardPoint;

    @ApiModelProperty(value = "訂單原始總價")
    @NotBlank(message = "訂單原始總價不得為空")
    private BigInteger amountOfOriginalPrice;

    @ApiModelProperty(value = "運費")
    @NotBlank(message = "運費不得為空")
    private BigInteger amountOfFreight;

    @ApiModelProperty(value = "折扣金額")
    @NotBlank(message = "折扣金額不得為空")
    private BigInteger amountOfDiscount;

    @ApiModelProperty(value = "實際支付金額")
    @NotBlank(message = "實際支付金額不得為空")
    private BigInteger amountOfActualPay;

    @ApiModelProperty(value = "創建時間")
    private LocalDateTime gmtCreate;
}
