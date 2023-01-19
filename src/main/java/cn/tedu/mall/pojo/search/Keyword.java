package cn.tedu.mall.pojo.search;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.annotations.Result;

import java.io.Serializable;

/**
 * @ClassName Keyword
 * @Version 1.0
 * @Description TODO
 * @Date 2023/1/19、下午4:25
 */
@Data
public class Keyword implements Serializable {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "關鍵字")
    private String keywordName;

    @ApiModelProperty(value = "計數")
    private Integer count;
    //預設計數為0
    public Keyword() {
        this.count = 0;
    }
}
