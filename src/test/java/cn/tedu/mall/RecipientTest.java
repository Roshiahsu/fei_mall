package cn.tedu.mall;

import cn.tedu.mall.mapper.RecipientMapper;
import cn.tedu.mall.pojo.domain.Recipient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @ClassName RecipientTest
 * @Version 1.0
 * @Description TODO
 * @Date 2023/1/7、下午9:05
 */
@SpringBootTest
public class RecipientTest {


    @Autowired
    private RecipientMapper recipientMapper;

    @Test
    public void insertTest(){
        Recipient recipient = new Recipient();
        recipient.setRecipientName("aa");
        recipient.setRecipientPhone("12313123");
        recipient.setRecipientAddress("dfwfkwngkwgn");

        int insert = recipientMapper.insert(recipient);
        System.out.println(recipient.getId());
    }
}
