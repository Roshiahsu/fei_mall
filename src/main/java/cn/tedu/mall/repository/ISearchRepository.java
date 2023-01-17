package cn.tedu.mall.repository;

import cn.tedu.mall.pojo.search.ProductForEs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.stereotype.Repository;

/**
 * @ClassName ISearchRepository
 * @Version 1.0
 * @Description ES交互功能
 * @Date 2023/1/17、下午8:39
 */
@Repository
public interface ISearchRepository extends ElasticsearchRepository<ProductForEs,Long> {

    @Query("{\n" +
            "    \"bool\": {\n" + //bool類型，代表true才有結果
            "      \"should\": [\n" + //should 代表or ，must 代表and
            "        {\"match\": {\"product_name\": \"?0\"}},\n" + //搜尋商品名稱
            "        {\"match\": {\"brand_name\": \"?0\"}},\n" + //搜尋品牌名稱
            "        {\"match\": {\"description\": \"?0\"}},\n" + //搜尋商品描述
            "        {\"match\": {\"keywords\": \"?0\"}}\n" +//搜尋關鍵字
            "      ]\n" +
            "    }\n" +
            "  }")
    Page<ProductForEs> querySearch(String keyword, Pageable pageable);

}
