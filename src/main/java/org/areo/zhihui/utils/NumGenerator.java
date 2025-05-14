//package org.areo.zhihui.utils;
//
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import org.areo.zhihui.mapper.UserMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.concurrent.atomic.AtomicInteger;
//@Component
//@RequiredArgsConstructor(onConstructor_ = {@Autowired})
//public class NumGenerator {
//
//    private final UserMapper userMapper;
//    private final AtomicInteger counter = new AtomicInteger(0);
//
//    @PostConstruct
//    public void init() {
//        Integer maxNum = userMapper.selectMaxNumForUpdate();
//        counter.set(maxNum == null ? 0 : maxNum);
//    }
//
//    @Transactional(propagation = Propagation.REQUIRES_NEW)
//    public Integer getNextNum() {
//        // 双重检查（内存计数器+数据库保证）
//        int newNum = counter.incrementAndGet();
//        userMapper.verifyNumUnique(newNum); // 验证唯一性
//        return newNum;
//    }
//}
