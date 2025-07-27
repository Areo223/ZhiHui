package org.areo.zhihui.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.areo.zhihui.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ScheduleServiceImpl implements ScheduleService {
//
//    private final ScheduleMapper scheduleMapper;
//    private final CourseMapper courseMapper;
//    private final TeacherMapper teacherMapper;
//    private final TimeSlotMapper timeSlotMapper;
//    private final TeacherUnavailableMapper teacherUnavailableMapper;
//    private final ClassroomMapper classroomMapper;
//
//    public List<Schedule> autoSchedule(List<TeachingClass> classes) {
//        // 初始化课表列表
//        List<Schedule> result = new ArrayList<>();
//
//        // 1. 按优先级排序(课时多的优先，特殊课程优先)
//        classes.sort(Comparator.comparingInt(this::getPriority));
//
//        // 2. 为每个教学班安排时间和教室
//        for (TeachingClass tc : classes) {
//            Schedule schedule = findBestSchedule(tc, result);
//            if (schedule != null) {
//                result.add(schedule);
//            } else {
//                log.warn("无法为教学班 {} 安排课表", tc.getId());
//            }
//        }
//
//        return result;
//    }
//
//    // 获取教学班优先级
//    private int getPriority(TeachingClass tc) {
//        Course course = courseMapper.selectById(tc.getCourseId());
//        int priority = course.getWeeklyHours(); // 周课时多的优先,权重是1
//        switch (course.getCourseType()) {
//            case LAB:
//                priority += 10; // 实验课权重是5
//                break;
//            case PE:
//                priority += 100; // 体育课权重是100
//                break;
//            case ENGLISH:
//                priority += 100; // 英语课权重是2
//                break;
//            case ELECTIVE:
//                priority += 20; // 选修课权重是20
//                break;
//            case COMPULSORY:
//                priority += 50; // 必修课权重是50
//                break;
//            case MINOR:
//                priority += 1; // 辅修课权重是1
//                break;
//            default:
//                priority += 80; // 普通课权重是80
//        }
//        return priority;
//    }
//
//    // 寻找最佳排课方案
//    private Schedule findBestSchedule(TeachingClass tc, List<Schedule> existing) {
//        Course course = courseMapper.selectById(tc.getCourseId());
//        Teacher teacher = teacherMapper.selectById(tc.getTeacherId());
//
//        // 获取所有可能的时间片和教室组合
//        List<TimeSlot> TimeSlots = timeSlotMapper.selectList(null);
//        List<Classroom> classrooms = getAvailableClassrooms(course);
//
//        // 尝试所有可能的组合，寻找最优解
//        for (int day = 1; day <= 5; day++) { // 周一到周五
//            for (TimeSlot slot : TimeSlots) {
//                if (isTeacherAvailable(teacher, day, slot, existing)) {
//                    for (Classroom room : classrooms) {
//                        if (isClassroomAvailable(room, day, slot, existing)) {
//                            Schedule schedule = new Schedule();
//                            schedule.setTeachingClassId(tc.getId());
//                            schedule.setClassroomId(room.getId());
//                            schedule.setTimeSlotId(slot.getId());
//                            schedule.setDayOfWeek(day);
//                            schedule.setWeekPattern("both"); // 默认每周都上
//                            return schedule;
//                        }
//                    }
//                }
//            }
//        }
//        return null;
//    }
//
//
//    // 检查教师是否可用
//    private boolean isTeacherAvailable(Teacher teacher, int day, TimeSlot slot,
//                                       List<Schedule> existing) {
//        // 检查教师不可用时间表
//        if (teacherUnavailableMapper.isUnavailable(teacher.getId(), day, slot.getId())) {
//            return false;
//        }
//
//        // 检查已有排课中该教师是否已有课
//        return existing.stream()
//                .noneMatch(s -> s.getTeachingClass().getTeacherId().equals(teacher.getId())
//                        && s.getDayOfWeek() == day
//                        && s.getTimeSlotId().equals(slot.getId()));
//    }
//
//    // 检查教室是否可用
//    private boolean isClassroomAvailable(Classroom room, int day, TimeSlot slot,
//                                         List<Schedule> existing) {
//        return existing.stream()
//                .noneMatch(s -> s.getClassroomId().equals(room.getId())
//                        && s.getDayOfWeek() == day
//                        && s.getTimeSlotId().equals(slot.getId()));
//    }
//


}
