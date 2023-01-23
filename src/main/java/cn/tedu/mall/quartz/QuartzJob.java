package cn.tedu.mall.quartz;

import cn.tedu.mall.repository.IBrandRepository;
import cn.tedu.mall.repository.IKeywordRepository;
import cn.tedu.mall.repository.IProductRepository;
import cn.tedu.mall.service.ISearchService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

/**
 * @ClassName QuartzJob
 * @Version 1.0
 * @Description TODO
 * @Date 2023/1/19、下午6:46
 */
@Slf4j
public class QuartzJob implements Job {

    @Autowired
    private ISearchService searchService;

    @Autowired
    private IKeywordRepository keywordRepository;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.debug("開始更新redis中的資料");
        //從redis中獲取資料，並更新到database中
        keywordRepository.updateDatabaseFromRedis();
        //更新ES
        searchService.loadProducts();
    }
}
