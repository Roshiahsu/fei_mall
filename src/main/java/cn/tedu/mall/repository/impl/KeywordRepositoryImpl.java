package cn.tedu.mall.repository.impl;

import cn.tedu.mall.mapper.KeywordMapper;
import cn.tedu.mall.repository.IKeywordRepository;
import cn.tedu.mall.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName KeywordRepositoryImpl
 * @Version 1.0
 * @Description TODO
 * @Date 2023/1/19、下午5:05
 */
@Repository
@Slf4j
public class KeywordRepositoryImpl implements IKeywordRepository {

    /**
     * 搜尋功能mapper注入
     */
    @Autowired
    private KeywordMapper keywordMapper;
    /**
     * Redis注入
     */
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 將資料加載到redis
     */
    @Override
    public void putList() {
        //刪除資料，避免資料重複
        deleteList();
        log.debug("開始將關鍵字加載到redis");
        //從mysql中獲取關鍵字列表
        List<String> keywordsVO = keywordMapper.listKeywordsOrderByCount();
        //從redis工具包獲取關鍵字key
        String key = RedisUtils.KEY_PREFIX_KEYWORD_LIST;
        //rightPush把關鍵字資料放入redis
        for (String keyword : keywordsVO) {
            redisTemplate.opsForList().rightPush(key,keyword);
        }
    }

    /**
     * 刪除redis中的資料
     */
    @Override
    public void deleteList() {
        log.debug("開始刪除redis中的關鍵字");
        //從redis工具包獲取關鍵字key
        String key = RedisUtils.KEY_PREFIX_KEYWORD_LIST;
        //刪除redis中的資料
        redisTemplate.delete(key);
    }

    /**
     * 從redis中獲取資料
     * @return
     */
    @Override
    public List<String> getList() {
        log.debug("開始從redis中獲取資料");
        //從redis工具包獲取關鍵字key
        String key = RedisUtils.KEY_PREFIX_KEYWORD_LIST;
        List<Object> range = redisTemplate.opsForList().range(key, 0, -1);
        List<String> keywordVO = new ArrayList<>();
        for (Object o : range) {
            keywordVO.add((String)o);
        }
        return keywordVO;
    }
}
