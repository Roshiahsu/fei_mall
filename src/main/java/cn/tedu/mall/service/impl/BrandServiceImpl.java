package cn.tedu.mall.service.impl;

import cn.tedu.mall.exception.ServiceException;
import cn.tedu.mall.mapper.BrandMapper;
import cn.tedu.mall.pojo.brand.Brand;
import cn.tedu.mall.repository.IBrandRepository;
import cn.tedu.mall.service.IBrandService;
import cn.tedu.mall.web.JsonPage;
import cn.tedu.mall.web.ServiceCode;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName BrandServiceImpl
 * @Version 1.0
 * @Description 品牌管理Service
 * @Date 2023/1/9、下午9:51
 */
@Service
@Slf4j
public class BrandServiceImpl implements IBrandService {

    /**
     * 品牌Mapper
     */
    @Autowired
    private BrandMapper brandMapper;

    /**
     * 品牌Repository(redis)
     */
    @Autowired
    private IBrandRepository brandRepository;

    /**
     * 新增品牌
     * @param brand 品牌資料
     */
    @Override
    public void insert(Brand brand) {
        log.debug("開始新增品牌");
        //檢查是否已經添加過相同品牌
        int count = brandMapper.countByBrandName(brand);
        if (count >0){
            throw new ServiceException(ServiceCode.ERR_CONFLICT,"品牌名稱已存在!");
        }
        //新增品牌
        int rows = brandMapper.insert(brand);
        if(rows!=1){
            throw new ServiceException(ServiceCode.ERR_INSERT,"伺服器忙碌中請稍候!");
        }
        //新增完成，更新Redis
        brandRepository.putList();
    }

    /**
     * 根據pageNum獲取品牌分頁列表
     * @param pageNum 顯示頁數
     * @return
     */
    @Override
    public JsonPage<Brand> listBrand(Integer pageNum) {
        log.debug("開始獲取品牌列表");
        //固定每頁顯示項目為20
        Integer pageSize =20;
        PageHelper.startPage(pageNum,pageSize);
        //從redis中獲取品牌列表
        List<Brand> brands = brandRepository.getList();

        return JsonPage.restPage(new PageInfo<>(brands));
    }

    /**
     * 根據id刪除品牌
     * @param id 品牌id
     */
    @Override
    public void deleteBrandById(Long id) {
        log.debug("開始刪除品牌");
        int rows = brandMapper.deleteBrandById(id);
        if(rows != 1){
            throw new ServiceException(ServiceCode.ERR_DELETE,"伺服器忙碌請稍候!");
        }
        //刪除完成，更新Redis
        brandRepository.putList();
    }

    /**
     * 根據id修改品牌資訊
     * @param brand 品牌資訊
     */
    @Override
    public void updateById(Brand brand) {
        log.debug("開始更新品牌資訊");
        int rows = brandMapper.updateById(brand);
        if(rows != 1){
            throw new ServiceException(ServiceCode.ERR_UPDATE,"伺服器忙碌請稍候!");
        }
        //刪除完成，更新Redis
        brandRepository.putList();
    }
}
