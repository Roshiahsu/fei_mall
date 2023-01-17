package cn.tedu.mall.web;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName JsonPage
 * @Version 1.0
 * @Description TODO
 * @Date 2022/12/31、上午5:26
 */
@Data
public class JsonPage<T> implements Serializable {

    @ApiModelProperty(value = "總頁數")
    private Integer totalPage;

    @ApiModelProperty(value = "總條數")
    private Long total;

    @ApiModelProperty(value = "當前頁面")
    private Integer pageNum;

    @ApiModelProperty(value = "每頁條數")
    private Integer pageSize;

    @ApiModelProperty(value = "分頁數據")
    private List<T> list;



    public static <T> JsonPage<T> restPage(PageInfo<T> pageInfo) {
        JsonPage<T> result = new JsonPage<T>();
        result.setTotalPage(pageInfo.getPages());
        result.setPageNum(pageInfo.getPageNum());
        result.setPageSize(pageInfo.getPageSize());
        result.setTotal(pageInfo.getTotal());
        result.setList(pageInfo.getList());
        return result;
    }

}
