package cn.tedu.mall.pojo.product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName ProductTypeListVO
 * @Version 1.0
 * @Description 推播種類 VO
 * @Date 2023/1/13、下午11:25
 */
@Data
public class ProductTypeListVO implements Serializable {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "推播種類")
    private String name;
}
