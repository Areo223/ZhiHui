package org.areo.zhihui;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.areo.zhihui.mapper.CourseOfferingMapper;
import org.areo.zhihui.mapper.StudentMapper;
import org.areo.zhihui.mapper.UserMapper;
import org.areo.zhihui.pojo.entity.CourseOffering;
import org.areo.zhihui.pojo.entity.User;
import org.areo.zhihui.utils.enums.RoleEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.util.List;

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
    @Qualifier("userMapper")
    @Autowired
    private UserMapper userMapper;

    @Test
    void testMail() throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        //生成随机验证码
        String code = "123456";
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        //设置一个html邮件信息
        helper.setText("<p style='color: blue'>你的验证码为：" + code + "(有效期为一分钟)</p>", true);
        //设置邮件主题名
        helper.setSubject("ZhiHui验证码----验证码");
        //发给谁-》邮箱地址
        helper.setTo("3049148371@qq.com");
        //谁发的-》发送人邮箱
        helper.setFrom("18205366556@163.com");
        mailSender.send(mimeMessage);
    }

    //生成选课测试用例
    @Autowired
    private CourseOfferingMapper courseOfferingMapper;
    @Test
    void testCourseOffering() {
        List<CourseOffering> courseOfferings = courseOfferingMapper.selectList(null);
        List<User> students = userMapper.selectList(new QueryWrapper<User>().eq("role", RoleEnum.STUDENT));
        for (CourseOffering courseOffering : courseOfferings) {
            for (User student : students) {
                System.out.println(student.getIdentifier()+",Ab12345678,"+courseOffering.getId());
            }
        }
    }


}
