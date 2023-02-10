package cn.tedu.mall.service;

/**
 * @ClassName IMailService
 * @Version 1.0
 * @Description TODO
 * @Date 2023/2/10、上午10:37
 */
public interface IMailService {
    /**
     * 發送Email
     * @param to 發送給誰
     * @param subject
     * @param body
     */
    void sendEmail(String to, String subject, String body);
}
