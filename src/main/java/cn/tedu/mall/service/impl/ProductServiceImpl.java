package cn.tedu.mall.service.impl;

import cn.tedu.mall.exception.ServiceException;
import cn.tedu.mall.mapper.ProductMapper;
import cn.tedu.mall.mapper.ProductTypeMapper;
import cn.tedu.mall.pojo.product.ProductAddNewDTO;
import cn.tedu.mall.pojo.product.ProductVO;
import cn.tedu.mall.pojo.product.ProductTypeListVO;
import cn.tedu.mall.pojo.product.ProductUpdateDTO;
import cn.tedu.mall.repository.IProductRepository;
import cn.tedu.mall.service.IProductService;
import cn.tedu.mall.utils.RedisUtils;
import cn.tedu.mall.web.JsonPage;
import cn.tedu.mall.web.ServiceCode;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName ProductServiceImpl
 * @Version 1.0
 * @Description 商品相關Service
 * @Date 2022/12/26、上午2:28
 */
@Service
@Slf4j
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductTypeMapper productTypeMapper;

    @Autowired
    private IProductRepository productRepository;

    /**
     * 新增商品
     * @param productAddNewDTO 商品資訊
     */
    @Override
    public void insert(ProductAddNewDTO productAddNewDTO) {
        log.debug("添加商品Service");
        //檢查商品是否重複
        int count = productMapper.countByName(productAddNewDTO.getProductName());
        if(count !=0){
            throw new ServiceException(ServiceCode.ERR_UPDATE,"商品重複！！");
        }
        LocalDateTime gmtExp = productAddNewDTO.getGmtExp();
        if(gmtExp != null){
            //修改時間

            gmtExp = gmtExp.plusHours(8);
            productAddNewDTO.setGmtExp(gmtExp);
        }
        //資料寫入資料庫
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
        log.debug("刪除商品Service");
        productMapper.deleteByIds(ids);
    }

    /**
     * 獲取商品列表
     * @param typeId
     * @return
     */
    @Override
    public JsonPage<ProductVO> listProduct(Integer pageNum, Integer pageSize, Integer typeId) {
        log.debug("獲取商品列表Service");

        List<ProductVO> list =null;
        //全品項使用分頁查詢直接查詢mysql
        if(typeId == RedisUtils.ALL_PRODUCT){
            PageHelper.startPage(pageNum,pageSize);
            list = productMapper.listProduct(typeId);
        }else{
            list = productRepository.getList(typeId,pageNum,pageSize);
        }
        //商品名稱太長，縮排
        for (ProductVO vo : list) {
            String productName = vo.getProductName();
            if(productName.length()>15){
                productName= productName.substring(0,10)+"...";
                vo.setProductName(productName);
            }
        }
        return JsonPage.restPage(new PageInfo<>(list));
    }

    /**
     * 根據id獲取商品詳情
     * @param id
     * @return
     */
    @Override
    public ProductVO getById(Long id) {
        log.debug("獲取商品詳情Service");
        return productMapper.getById(id);
    }

    /**
     * 獲取推播列表
     * @return
     */
    @Override
    public List<ProductTypeListVO> listProductType() {
        log.debug("開始獲取推播列表");
        return productTypeMapper.listProductType();
    }

    @Override
    public void updateById(ProductUpdateDTO productUpdateDTO) {

        //判斷接收到的值是否是String類型，如果是代表該屬性沒修改
        if(!productUpdateDTO.getBrandName().getClass().equals(String.class)){
            //將收到的數值轉成Long
            Long brandId = Long.valueOf(productUpdateDTO.getBrandName().toString());
            productUpdateDTO.setBrandId(brandId);
        }

        if(!productUpdateDTO.getProductTypeName().getClass().equals(String.class)){
            Integer productTypeId = Integer.valueOf(productUpdateDTO.getProductTypeName().toString());
            productUpdateDTO.setProductTypeId(productTypeId);
        }


        int rows = productMapper.updateById(productUpdateDTO);
        if(rows != 1){
            throw new ServiceException(ServiceCode.ERR_UPDATE,"伺服器忙碌中，請稍後再試!!");
        }
    }
}
