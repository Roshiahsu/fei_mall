package cn.tedu.mall.pojo.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName OrderAddNewDTO
 * @Version 1.0
 * @Description 前端發起新增訂單請求的資料
 * @Date 2023/1/1、下午8:43
 */
@Data
public class OrderAddNewDTO implements Serializable {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "用戶id")
    private Long userId;

    @ApiModelProperty(value = "積分")
    private Integer rewardPoint;

    @ApiModelProperty(value = "訂單原始總價")
    @NotBlank(message = "訂單原始總價不得為空")
    private BigInteger amountOfOriginalPrice;

    @ApiModelProperty(value = "運費")
    private BigInteger amountOfFreight;

    @ApiModelProperty(value = "折扣金額")
    private BigInteger amountOfDiscount;

    @ApiModelProperty(value = "實際支付金額")
    @NotBlank(message = "實際支付金額不得為空")
    private BigInteger amountOfActualPay;

    @ApiModelProperty(value = "訂單商品列表")
    private List<OrderItemAddNewDTO> orderItems;

    @ApiModelProperty(value = "收件人姓名")
    private String recipientName;

    @ApiModelProperty(value = "收件人電話")
    private String recipientPhone;

    @ApiModelProperty(value = "收件人地址")
    private String recipientAddress;

}
