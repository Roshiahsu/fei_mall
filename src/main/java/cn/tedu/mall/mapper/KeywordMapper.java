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
    /**
     * 新增關鍵字
     * @param keyword 關鍵字
     */
    void insert(Keyword keyword);

    /**
     * 根據關鍵字修改計數(count)
     * @param keyword 關鍵字
     */
    void updateCount(Keyword keyword);

    /**
     * 獲取關鍵字列表
     * @return
     */
    List<Keyword> listKeywordsOrderByCount();

    /**
     * 根據關鍵字統計數量
     * @param keywordName
     * @return
     */
    int countByKeywordName(String keywordName);
}
