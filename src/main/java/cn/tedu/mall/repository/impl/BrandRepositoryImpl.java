package cn.tedu.mall.repository.impl;

import cn.tedu.mall.mapper.BrandMapper;
import cn.tedu.mall.pojo.brand.Brand;
import cn.tedu.mall.repository.IBrandRepository;


import static cn.tedu.mall.utils.RedisUtils.KEY_PREFIX_BRAND_LIST;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName BrandRepositoryImpl
 * @Version 1.0
 * @Description TODO
 * @Date 2023/1/15、下午1:18
 */
@Repository
@Slf4j
public class BrandRepositoryImpl implements IBrandRepository {

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void putList() {
        log.debug("獲取品牌資料");
        //從Mysql中獲取品牌資料
        List<Brand> brandListVO= brandMapper.listBrand();
        String key = KEY_PREFIX_BRAND_LIST;

        for (Brand brand : brandListVO) {
            redisTemplate.opsForList().rightPush(key,brand);
        }
    }

    @Override
    public void deleteList() {
        log.debug("開始刪除redis中的List數據");
        redisTemplate.delete(KEY_PREFIX_BRAND_LIST);
    }

    @Override
    public List<Brand> getList() {
        log.debug("從Redis中獲取資料");
        List<Object> brandVO = redisTemplate.opsForList().range(KEY_PREFIX_BRAND_LIST, 0, -1);
        List<Brand> brandListVO = new ArrayList<>();
        for (Object brand : brandVO) {
            brandListVO.add((Brand)brand);
        }
        return brandListVO;
    }
}
