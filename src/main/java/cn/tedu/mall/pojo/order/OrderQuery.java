package cn.tedu.mall.pojo.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @ClassName OrderQueryDTO
 * @Version 1.0
 * @Description TODO
 * @Date 2023/2/5、下午1:00
 */
@Data
public class OrderQuery implements Serializable {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "訂單編號")
    private String sn;

    @ApiModelProperty(value = "訂購日期開始")
    private LocalDateTime gmtCreateStart;

    @ApiModelProperty(value = "訂購日期結束")
    private LocalDateTime gmtCreateEnd;

    @ApiModelProperty(value = "訂購人關鍵字")
    private String receiverKeyword;
}
