package org.areo.zhihui.services;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.areo.zhihui.mapper.EnrollmentMapper;
import org.areo.zhihui.pojo.entity.Enrollment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EnrollmentBatchService {

    private final BlockingQueue<Enrollment> batchQueue = new LinkedBlockingQueue<>();
    // 退课记录队列
    private final BlockingQueue<Enrollment> dropQueue = new LinkedBlockingQueue<>();

    private static final int BATCH_SIZE = 100;
    private static final long MAX_WAIT = 1000;
    private final EnrollmentMapper enrollmentMapper;
    private final CourseCapacityService courseCapacityService;

    @PostConstruct
    // 初始化时启动批处理线程
    public void init() {
        // 启动批处理线程
        new Thread(this::processBatch).start();
        // 启动退课处理线程
        new Thread(this::processDrop).start();
    }

    // 加入选课队列
    public void addToBatch(Enrollment enrollment) {
        batchQueue.add(enrollment);
    }
    // 加入退课队列
    public void addToDropQueue(Enrollment enrollment) {
        dropQueue.add(enrollment);
    }
    // 处理退课任务
    private void processDrop() {
        List<Enrollment> dropList = new ArrayList<>(BATCH_SIZE);
        while (true) {
            try {
                // 阻塞获取第一个元素
                Enrollment first = dropQueue.take();
                dropList.add(first);

                // 非阻塞获取剩余元素
                for (int i = 1; i < BATCH_SIZE; i++) {
                    Enrollment item = dropQueue.poll(MAX_WAIT, TimeUnit.MILLISECONDS);
                    if (item == null) break;
                    dropList.add(item);
                }

                // 执行退课操作
                if (!dropList.isEmpty()) {
                    asyncDrop(dropList);
                    dropList.clear();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
    // 异步退课
    @Async
    public void asyncDrop(List<Enrollment> dropList) {
        // 执行退课操作
        enrollmentMapper.deleteBatch(dropList);
        // 记录退课成功
        dropList.forEach(e ->
                courseCapacityService.recordDrop(e.getCourseOfferingId())
        );
    }


    // 处理批量插入任务
    private void processBatch() {
        List<Enrollment> batchList = new ArrayList<>(BATCH_SIZE);
        while (true) {
            try {
                // 阻塞获取第一个元素
                Enrollment first = batchQueue.take();
                batchList.add(first);

                // 非阻塞获取剩余元素
                for (int i = 1; i < BATCH_SIZE; i++) {
                    Enrollment item = batchQueue.poll(MAX_WAIT, TimeUnit.MILLISECONDS);
                    if (item == null) break;
                    batchList.add(item);
                }

                // 执行批量插入
                if (!batchList.isEmpty()) {
                    asyncBatchInsert(batchList);
                    batchList.clear();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    @Async
    // 异步批量插入
    public void asyncBatchInsert(List<Enrollment> enrollments) {
        enrollmentMapper.insert(enrollments);

        // 记录成功插入的选课记录
        enrollments.forEach(e ->
                courseCapacityService.recordEnrollment(e.getCourseOfferingId())
        );
    }
}

