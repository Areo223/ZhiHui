package org.areo.zhihui.services;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.areo.zhihui.mapper.CourseOfferingMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CourseCapacityService {
    // 合并更新的时间窗口(毫秒)
    private static final long UPDATE_WINDOW = 1000;
    // 容量变更记录Map: courseId -> delta
    private final ConcurrentMap<Integer, AtomicInteger> capacityChanges = new ConcurrentHashMap<>();
    // 定时任务线程池
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final CourseOfferingMapper courseOfferingMapper;

    public CourseCapacityService(@Qualifier("courseOfferingMapper") CourseOfferingMapper courseOfferingMapper) {
        this.courseOfferingMapper = courseOfferingMapper;
    }

    @PostConstruct
    public void init() {
        // 启动定时刷新任务
        scheduler.scheduleAtFixedRate(this::flushCapacityChanges,
                UPDATE_WINDOW, UPDATE_WINDOW, TimeUnit.MILLISECONDS);
    }

    // 记录选课成功
    public void recordEnrollment(Integer courseOfferingId) {
        capacityChanges
                .computeIfAbsent(courseOfferingId, k -> new AtomicInteger(0))
                .decrementAndGet();
    }

    // 记录退选成功
    public void recordDrop(Integer courseOfferingId) {
        capacityChanges
                .computeIfAbsent(courseOfferingId, k -> new AtomicInteger(0))
                .incrementAndGet();
    }

    // 定时刷新到数据库
    private void flushCapacityChanges() {
        if (capacityChanges.isEmpty()) return;

        // 复制当前变更并清空
        Map<Integer, Integer> changes = new HashMap<>();
        capacityChanges.forEach((courseId, delta) -> {
            changes.put(courseId, delta.getAndSet(0));
            // 移除delta为0的条目
            if (delta.get() == 0) {
                capacityChanges.remove(courseId);
            }
        });

        // 执行批量更新
        if (!changes.isEmpty()) {
            courseOfferingMapper.batchUpdateCapacity(changes);
        }
    }

    @PreDestroy
    public void destroy() {
        // 应用关闭前强制刷新
        flushCapacityChanges();
        scheduler.shutdown();
    }
}