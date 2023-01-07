package cn.tedu.mall.mapper;

import cn.tedu.mall.pojo.domain.Recipient;
import org.springframework.stereotype.Repository;

/**
 * @ClassName RecipientMapper
 * @Version 1.0
 * @Description 收件人資訊
 * @Date 2023/1/7、下午8:47
 */
@Repository
public interface RecipientMapper {
    /**
     * 新增收件人資訊
     * @param recipient 收件人資訊(姓名、電話、地址)
     * @return
     */
    int insert(Recipient recipient);

    /**
     * 根據收件人資料獲取id
     * @param recipient
     * @return
     */
    Long getRecipientId(Recipient recipient);
}
