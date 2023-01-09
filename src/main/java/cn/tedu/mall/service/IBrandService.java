package cn.tedu.mall.service;

import cn.tedu.mall.pojo.brand.Brand;
import cn.tedu.mall.pojo.product.ProductListVO;
import cn.tedu.mall.web.JsonPage;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName IBrandService
 * @Version 1.0
 * @Description TODO
 * @Date 2023/1/9、下午9:51
 */
@Transactional
public interface IBrandService {

    void insert(Brand brand);

    JsonPage<Brand> listBrand(Integer pageNum);
}
