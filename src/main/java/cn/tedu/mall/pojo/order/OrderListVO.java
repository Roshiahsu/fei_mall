package cn.tedu.mall.pojo.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @ClassName OrderListVO
 * @Version 1.0
 * @Description 訂單列表VO
 * @Date 2023/1/6、下午9:23
 */
@Data
public class OrderListVO implements Serializable {


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
}
