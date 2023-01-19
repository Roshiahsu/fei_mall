package cn.tedu.mall.mapper;

import cn.tedu.mall.pojo.search.Keyword;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName KeywordMapper
 * @Version 1.0
 * @Description TODO
 * @Date 2023/1/19、下午4:13
 */
@Repository
public interface KeywordMapper {

    void insert(Keyword keyword);

    void updateCount(String keyword);

    List<String> listKeywordsOrderByCount();

    int countByKeywordName(String keywordName);
}
