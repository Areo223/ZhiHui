package org.areo.zhihui.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class MailMsg {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //发送邮箱和链接
    public boolean linkMail(String email, String Link) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setText("<p style='color: blue'>你正在尝试重置密码！请点击以下链接重置密码：<a href='"+Link+"'>"+Link+"</a></p>", true);
        helper.setSubject("ZhiHui验证码----验证码");
        helper.setTo(email);
        helper.setFrom("18205366556@163.com");
        mailSender.send(mimeMessage);
        return true;
    }



    public boolean codeMail(String email,String identifier) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        //生成随机验证码
        String code = CodeGeneratorUtil.generateCode(6);
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        //设置一个html邮件信息
        helper.setText("<p style='color: blue'>你正在尝试重置密码！你的验证码为：" + code + "(有效期为一分钟)</p>", true);
        //设置邮件主题名
        helper.setSubject("ZhiHui验证码----验证码");
        //发给谁-》邮箱地址
        helper.setTo(email);
        //谁发的-》发送人邮箱
        helper.setFrom("18205366556@163.com");

        redisTemplate.opsForValue().set(identifier, code, Duration.ofMinutes(1));
        mailSender.send(mimeMessage);
        return true;
    }
}