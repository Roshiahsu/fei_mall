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
public class OrderQueryDTO implements Serializable {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "訂單編號")
    private String sn;

    @ApiModelProperty(value = "訂購日期")
    private LocalDateTime gmtCreate;

    @ApiModelProperty(value = "查詢頁數")
    private Integer pageNum;

    @ApiModelProperty(value = "顯示大小")
    private Integer pageSize;

    @ApiModelProperty(value = "訂購人關鍵字")
    private String receiverKeyword;
}
