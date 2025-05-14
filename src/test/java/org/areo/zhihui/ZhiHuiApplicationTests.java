package org.areo.zhihui;

import org.areo.zhihui.mapper.StudentMapper;
import org.areo.zhihui.pojo.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

}
