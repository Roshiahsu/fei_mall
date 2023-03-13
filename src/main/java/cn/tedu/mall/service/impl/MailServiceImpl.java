package cn.tedu.mall.service.impl;

import cn.tedu.mall.service.IMailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @ClassName MailServiceImpl
 * @Version 1.0
 * @Description TODO
 * @Date 2023/2/10、上午10:40
 */
@Service
@Slf4j
public class MailServiceImpl implements IMailService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public MailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        //發送給
        message.setTo(to);
        //發送主題
        message.setSubject(subject);
        //發送內文
        message.setText(body);
        javaMailSender.send(message);
    }
}
