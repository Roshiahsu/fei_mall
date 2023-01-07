package cn.tedu.mall.pojo.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @ClassName Recipient
 * @Version 1.0
 * @Description 收件人
 * @Date 2023/1/7、下午8:43
 */
@Data
public class Recipient implements Serializable {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "收件人姓名")
    @NotBlank(message = "收件人姓名不得為空")
    private String recipientName;

    @ApiModelProperty(value = "收件人電話")
    @NotBlank(message = "收件人電話不得為空")
    private String recipientPhone;

    @ApiModelProperty(value = "收件人地址")
    @NotBlank(message = "收件人地址不得為空")
    private String recipientAddress;
}
