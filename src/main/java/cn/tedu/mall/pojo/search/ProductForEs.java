package cn.tedu.mall.pojo.search;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @ClassName ProductForEs
 * @Version 1.0
 * @Description TODO
 * @Date 2023/1/17、下午8:22
 */
@Data
@Document(indexName = "fei_mall_index")
@ApiModel(value="對應ES搜索的文檔entity")
public class ProductForEs implements Serializable {

    @Id
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "分類id")
    private Long categoryId;

    @ApiModelProperty(value = "品牌id")
    private Long brandId;

    @Field(name = "brand_name",type = FieldType.Text,
            analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    @ApiModelProperty(value = "品牌名稱")
    private String brandName;

    @Field(name = "product_name",type = FieldType.Text,
            analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    @ApiModelProperty(value = "商品名稱")
    @NotBlank(message = "請輸入商品名稱")
    private String productName;

    @Field(name = "description",type = FieldType.Text,
            analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    @ApiModelProperty(value = "商品描述")
    private String description;

    @Field(name = "keywords",type = FieldType.Text,
            analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    @ApiModelProperty(value = "關鍵字")
    private String keywords;

    @Field(type = FieldType.Keyword,index = false)
    @ApiModelProperty(value = "圖片路徑")
    private String picture;

    @Field(name = "tags",type = FieldType.Text,
            analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    @ApiModelProperty(value = "標籤")
    private String tags;

    @Field(name="price")
    @ApiModelProperty(value = "價錢")
    private Integer price;

    @Field(name="price")
    @ApiModelProperty(value = "庫存")
    private Integer stock;

    @Field(type=FieldType.Date)
    @ApiModelProperty(value = "有效期限")
    private LocalDate gmtExp;

    @ApiModelProperty(value = "推播種類編號")
    private Integer productTypeId;

    @ApiModelProperty(value = "推播種類名稱")
    private String productTypeName;

}
