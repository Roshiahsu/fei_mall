package cn.tedu.mall.service.impl;

import cn.tedu.mall.repository.IKeywordRepository;
import cn.tedu.mall.service.IKeywordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName KeywordServiceImpl
 * @Version 1.0
 * @Description TODO
 * @Date 2023/1/19、下午5:49
 */
@Service
@Slf4j
public class KeywordServiceImpl implements IKeywordService {

    @Autowired
    private IKeywordRepository keywordRepository;

    @Override
    public List<String> listKeyword() {
        log.debug("開始獲取關鍵字資料Service");
        log.debug("開始從redis中獲取資料");
        List<String> list = keywordRepository.getList();
        return list;
    }
}
