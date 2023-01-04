package cn.tedu.mall.pojo.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @ClassName OrderAddVO
 * @Version 1.0
 * @Description 新增訂單完成響應給前端的資料
 * @Date 2023/1/2、上午4:10
 */
@Data
public class OrderAddVO implements Serializable {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "訂單編號")
    private String sn;

    @ApiModelProperty(value = "實際支付金額")
    private BigInteger payAmount;
}
