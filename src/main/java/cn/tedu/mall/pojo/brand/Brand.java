package cn.tedu.mall.pojo.brand;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @ClassName Brand
 * @Version 1.0
 * @Description TODO
 * @Date 2023/1/9、下午8:23
 */
@Data
public class Brand implements Serializable {

    @ApiModelProperty(value = "品牌id")
    private Long id;

    @ApiModelProperty(value = "品牌名稱")
    @NotBlank(message = "請輸入品牌名稱")
    private String brandName;
}
