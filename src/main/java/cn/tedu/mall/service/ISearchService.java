package cn.tedu.mall.service;

import cn.tedu.mall.pojo.search.ProductForEs;
import cn.tedu.mall.web.JsonPage;

/**
 * @ClassName ISearchService
 * @Version 1.0
 * @Description TODO
 * @Date 2023/1/17、下午8:50
 */
public interface ISearchService {

    //向ES加載數據
    void loadProducts();

    //ES分頁查詢spu方法
    JsonPage<ProductForEs> search(String keyword, Integer page, Integer pageSize);
}
