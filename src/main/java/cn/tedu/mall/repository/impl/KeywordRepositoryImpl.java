package cn.tedu.mall.repository.impl;

import cn.tedu.mall.mapper.KeywordMapper;
import cn.tedu.mall.pojo.search.Keyword;
import cn.tedu.mall.repository.IKeywordRepository;
import cn.tedu.mall.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 將資料加載到redis
     */
    @Override
    public void putList() {
        //刪除資料，避免資料重複
        deleteList();
        log.debug("開始將關鍵字加載到redis");
        //從mysql中獲取關鍵字列表
        List<Keyword> keywordsVO = keywordMapper.listKeywordsOrderByCount();
        for (Keyword keyword : keywordsVO) {
            log.debug("獲取的資料>>>{}",keyword);
            String keywordName = keyword.getKeywordName();
            //從redis工具包獲取關鍵字key
            String key = RedisUtils.KEY_PREFIX_KEYWORD_LIST;
            //rightPush把關鍵字資料放入redis
           redisTemplate.opsForList().rightPush(key,keywordName);
        }
    }

    /**
     * 刪除redis中List的資料
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
     * 刪除redis中的Item
     */
    @Override
    public void deleteItem(String itemName) {
        log.debug("開始刪除redis中的關鍵字");
        //從redis工具包獲取關鍵字key
        String key = RedisUtils.KEY_PREFIX_KEYWORD;
        stringRedisTemplate.delete(key+itemName);
    }

    /**
     * 從redis list中獲取資料
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

    /**
     * 對關鍵字進行判斷
     * 如果存在則計數+1，如果不存在則新增關鍵字
     * @param keywordName 前端發送的關鍵字
     */
    public void initKeyword(String keywordName){
        log.debug("開始對關鍵字進行分析");
        //TODO 預計更改成在Redis中修改，修改後定期把redis中的資料寫入資料庫
        //從redis工具包獲取關鍵字key
        String key = RedisUtils.KEY_PREFIX_KEYWORD + keywordName;
        //increment() 如果 key值不存在，會創建一個並賦值1
        //如果key存在則，值+1
        stringRedisTemplate.boundValueOps(key).increment();
    }

    /**
     * 將redis中的資料寫入mysql
     */
    @Override
    public void updateDatabaseFromRedis() {
        //獲取前綴
        String key = RedisUtils.KEY_PREFIX_KEYWORD;
        //獲取該前綴下的所有key
        Set<String> keys = redisTemplate.keys(key.concat("*"));
        Keyword keyword = new Keyword();
        //遍歷key值
        for (String s : keys) {
            //取出後綴
            String keywordName = s.substring(s.lastIndexOf(":")+1);
            if (!"list".equals(keywordName)){
                String countString = stringRedisTemplate.boundValueOps(s).get();
                int count = Integer.parseInt(countString);
                keyword.setKeywordName(keywordName);
                keyword.setCount(count);
                //判斷該關鍵字是否存在database中
                int countByKeywordName = keywordMapper.countByKeywordName(keywordName);
                if(countByKeywordName !=0){
                    //已存在則更新
                    keywordMapper.updateCount(keyword);
                }else{
                    //不存在則新增
                    keywordMapper.insert(keyword);
                }
                //刪除redis中的資料
                deleteItem(keywordName);
            }
        }

    }
}
