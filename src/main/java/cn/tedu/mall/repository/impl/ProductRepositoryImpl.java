package cn.tedu.mall.repository.impl;

import cn.tedu.mall.mapper.ProductMapper;
import cn.tedu.mall.mapper.ProductTypeMapper;
import cn.tedu.mall.pojo.product.ProductVO;
import cn.tedu.mall.repository.IProductRepository;
import cn.tedu.mall.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ProductRepositoryImpl
 * @Version 1.0
 * @Description 商品repository
 * @Date 2023/1/15、下午2:47
 */
@Repository
@Slf4j
public class ProductRepositoryImpl implements IProductRepository {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductTypeMapper productTypeMapper;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public void putList(Integer typeId) {
        deleteList(typeId);
        log.debug("向Redis中寫入商品數據");
        List<ProductVO> productVOS = productMapper.listProduct(typeId);

        String key = getRedisKey(typeId);

        for (ProductVO productVO : productVOS) {
            redisTemplate.opsForList().rightPush(key,productVO);
        }
    }

    @Override
    public void deleteList(Integer typeId) {
        log.debug("開始刪除redis中的Product List數據");
        String key = getRedisKey(typeId);
        redisTemplate.delete(key);
    }

    @Override
    public List<ProductVO> getList(Integer typeId) {
        log.debug("從Redis中獲取Product資料");
        String key = getRedisKey(typeId);

        List<Object> productVO = redisTemplate.opsForList().range(key, 0, -1);
        List<ProductVO> productListVO = new ArrayList<>();

        for (Object vo : productVO) {
            productListVO.add((ProductVO)vo);
        }
        return productListVO;
    }

    public String getRedisKey(Integer typeId){
        String key ;
        //根據輸入的typeId決定redis中的key
        if (typeId.equals(RedisUtils.ALL_PRODUCT)){
            key = RedisUtils.getProductKey(RedisUtils.ALL_PRODUCT_NAME);
        }else if (typeId.equals(RedisUtils.NEW_PRODUCT)){
            key = RedisUtils.getProductKey(RedisUtils.NEW_PRODUCT_NAME);
        }else if (typeId.equals(RedisUtils.HOT_PRODUCT)){
            key = RedisUtils.getProductKey(RedisUtils.HOT_PRODUCT_NAME);
        }else
            key = RedisUtils.getProductKey(RedisUtils.DISCOUNTED_PRODUCT_NAME);

        return key;
    }
}
