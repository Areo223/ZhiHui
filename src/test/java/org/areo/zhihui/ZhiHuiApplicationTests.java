package org.areo.zhihui;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.areo.zhihui.mapper.StudentMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@SpringBootTest
class ZhiHuiApplicationTests {

    @Autowired
    private StudentMapper studentMapper;

//    @Test
//    void testInsert() {
//        Student student = new Student();
//        student.setStudentNumber("2023211005");
//        student.setName("lee");
//        student.setPassword("123456");
//        student.setInformation("后端");
//        student.setNum(2);
//        studentMapper.insert(student);
//    }

    //邮箱验证码测试
    @Autowired
    private JavaMailSender mailSender;
    @Test
    void testMail() throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        //生成随机验证码
        String code = "123456";
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        //设置一个html邮件信息
        helper.setText("<p style='color: blue'>三秦锅，你在搞什么飞机！你的验证码为：" + code + "(有效期为一分钟)</p>", true);
        //设置邮件主题名
        helper.setSubject("FlowerPotNet验证码----验证码");
        //发给谁-》邮箱地址
        //发给谁-》邮箱地址
        helper.setTo("3049148371@qq.com");
        //谁发的-》发送人邮箱
        //谁发的-》发送人邮箱
        helper.setFrom("18205366556@163.com");
        mailSender.send(mimeMessage);
    }

}
