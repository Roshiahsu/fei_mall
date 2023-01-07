package cn.tedu.mall.pojo.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;

/**
 * @ClassName OrderDetailVO
 * @Version 1.0
 * @Description 訂單詳情
 * @Date 2023/1/7、下午6:43
 */
@Data
public class OrderDetailVO implements Serializable {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "訂單編號")
    private String sn;

    @ApiModelProperty(value = "訂購日期")
    private LocalDate gmtCreate;

    @ApiModelProperty(value = "訂單狀態")
    private String statusName;

    @ApiModelProperty(value = "實際支付金額")
    private BigInteger amountOfActualPay;

    @ApiModelProperty(value = "收件人姓名")
    private String recipientName;

    @ApiModelProperty(value = "收件人電話")
    private String recipientPhone;

    @ApiModelProperty(value = "收件人地址")
    private String recipientAddress;

}
