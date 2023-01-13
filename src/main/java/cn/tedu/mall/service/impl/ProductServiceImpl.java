package cn.tedu.mall.service.impl;

import cn.tedu.mall.exception.ServiceException;
import cn.tedu.mall.mapper.ProductMapper;
import cn.tedu.mall.pojo.product.ProductAddNewDTO;
import cn.tedu.mall.pojo.product.ProductListVO;
import cn.tedu.mall.service.IProductService;
import cn.tedu.mall.service.IUploadService;
import cn.tedu.mall.web.JsonPage;
import cn.tedu.mall.web.ServiceCode;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.IOException;
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
    private IUploadService uploadService;

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
    public JsonPage<ProductListVO> listProduct(Integer pageNum,Integer pageSize,Long typeId) {
        log.debug("獲取商品列表Service");
        PageHelper.startPage(pageNum,pageSize);

        List<ProductListVO> list = productMapper.listProduct(typeId);

        //商品名稱太長，縮排
        for (ProductListVO vo : list) {
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
    public ProductListVO getById(Long id) {
        log.debug("獲取商品詳情Service");
        return productMapper.getById(id);
    }
}
