package cn.tedu.mall.service.impl;

import cn.tedu.mall.pojo.product.ProductVO;
import cn.tedu.mall.pojo.search.ProductForEs;
import cn.tedu.mall.repository.ISearchRepository;
import cn.tedu.mall.service.IProductService;
import cn.tedu.mall.service.ISearchService;
import cn.tedu.mall.utils.RedisUtils;
import cn.tedu.mall.web.JsonPage;
import com.google.j2objc.annotations.AutoreleasePool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName SearchServiceImpl
 * @Version 1.0
 * @Description 搜尋功能
 * @Date 2023/1/17、下午8:53
 */
@Service
@Slf4j
public class SearchServiceImpl implements ISearchService {

    @Autowired
    private IProductService productService;

    @Autowired
    private ISearchRepository searchRepository;

    /**
     * 加載數據到ES
     */
    @Override
    public void loadProducts() {
        log.debug("開始加載數據到ES");
        int i=1;
        int pages= 0;
        int pageSize =10;
        do{
            //將商品推播種類固定為全品項
            JsonPage<ProductVO> productVOS = productService.listProduct(i, pageSize, RedisUtils.ALL_PRODUCT);
            List<ProductForEs> esProducts = new ArrayList<>();
            for (ProductVO productVO : productVOS.getList()) {
                ProductForEs productForEs = new ProductForEs();
                BeanUtils.copyProperties(productVO,productForEs);
                esProducts.add(productForEs);
            }
            //數據保存到es
            searchRepository.saveAll(esProducts);
            log.info("成功加載第{}頁數據",i);
            pages=productVOS.getTotalPage();
            i++;
        }while(i<=pages);
    }

    /**
     * 使用ES進行查詢
     * @param keyword 查詢的關鍵字
     * @param page 頁碼
     * @param pageSize 每頁顯示數量
     * @return
     */
    @Override
    public JsonPage<ProductForEs> search(String keyword, Integer page, Integer pageSize) {
        log.debug("開始關鍵字查詢");
        Page<ProductForEs> products = searchRepository.querySearch(keyword, PageRequest.of(page - 1, pageSize));
        //將Page 轉換JsonPage
        JsonPage<ProductForEs> jsonPage = new JsonPage<>();
        jsonPage.getTotalPage();
        jsonPage.setPageSize(pageSize);
        jsonPage.setTotal(products.getTotalElements());
        jsonPage.setTotalPage(products.getTotalPages());
        jsonPage.setList(products.getContent());
        return jsonPage;
    }

    /**
     * 對關鍵字進行判斷
     * 如果存在則計數+1，如果不存在則新增關鍵字
     * @param keyword 前端發送的關鍵字
     */
    public void initKeyword(String keyword){
        log.debug("開始對關鍵字進行分析");
        //TODO 使用Redis 待完成

    }
}
