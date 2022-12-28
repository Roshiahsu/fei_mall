package cn.tedu.mall.service.impl;

import cn.tedu.mall.exception.ServiceException;
import cn.tedu.mall.mapper.ProductMapper;
import cn.tedu.mall.pojo.product.ProductAddNewDTO;
import cn.tedu.mall.pojo.product.ProductListVO;
import cn.tedu.mall.service.IProductService;
import cn.tedu.mall.web.ServiceCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName ProductServiceImpl
 * @Version 1.0
 * @Description TODO
 * @Date 2022/12/26、上午2:28
 */
@Service
@Slf4j
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    /**
     * 添加商品
     * @param productAddNewDTO
     */
    @Override
    public void insert(ProductAddNewDTO productAddNewDTO) {
        log.debug("productService.insert開始");
        //檢查商品是否重複
        int count = productMapper.countByName(productAddNewDTO.getProductName());
        if(count !=0){
            throw new ServiceException(ServiceCode.ERR_INSERT,"商品重複！！");
        }

        int rows = productMapper.insert(productAddNewDTO);
        if(rows != 1){
            throw new ServiceException(ServiceCode.ERR_INSERT,"伺服器忙碌中，請稍後再試!!");
        }
    }

    /**
     * 刪除商品
     * @param ids
     */
    @Override
    public void deleteByIds(Long... ids) {
        log.debug("productService.deleteByIds開始");
        productMapper.deleteByIds(ids);
    }

    /**
     * 獲取商品列表
     * @param typeId
     * @return
     */
    @Override
    public List<ProductListVO> listProduct(Long typeId) {
        log.debug("productService.listProduct開始");
        List<ProductListVO> vos = productMapper.listProduct(typeId);
        //TODO 增加分頁查詢
        return vos;
    }

    /**
     * 根據id獲取商品詳情
     * @param id
     * @return
     */
    @Override
    public ProductListVO getById(Long id) {
        log.debug("productService.getById開始");
        return productMapper.getById(id);
    }
}
