package cn.tedu.mall.pojo.product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @ClassName ProductAddNewDTO
 * @Version 1.0
 * @Description 發送修改商品請求的商品資料
 * @Date 2022/12/24、下午11:16
 */
@Data
public class ProductUpdateDTO implements Serializable {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "分類id")
    private Long categoryId;

    @ApiModelProperty(value = "品牌id")
    private Long brandId;

    @ApiModelProperty(value = "品牌名稱，前端將變更過的brandId以brandName傳送")
    private Object brandName;

    @ApiModelProperty(value = "商品名稱")
    private String productName;

    @ApiModelProperty(value = "商品描述")
    private String description;

    @ApiModelProperty(value = "關鍵字")
    private String keywords;

    @ApiModelProperty(value = "圖片路徑")
    private String picture;

    @ApiModelProperty(value = "標籤")
    private String tags;

    @ApiModelProperty(value = "價錢")
    private Integer price;

    @ApiModelProperty(value = "庫存")
    private Integer stock;

    @ApiModelProperty(value = "有效期限")
    private LocalDate gmtExp;

    @ApiModelProperty(value = "商品種類編號")
    private Integer productTypeId;

    @ApiModelProperty(value = "商品種類名稱，前端將變更過的productTypeId以productTypeName傳送")
    private Object productTypeName;
}
