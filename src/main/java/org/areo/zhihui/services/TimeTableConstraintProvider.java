package org.areo.zhihui.services;

import org.areo.zhihui.pojo.entity.TeachingSession;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import org.optaplanner.core.api.score.stream.Joiners;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;

@Service
public class TimeTableConstraintProvider implements ConstraintProvider {
    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{
                //硬约束
                teacherConflict(constraintFactory),
                classroomConflict(constraintFactory),
                classroomCapacity(constraintFactory),
                //软约束
                teacherTimePreference(constraintFactory),
//                courseInterval(constraintFactory),
//                //TODO:怎么维护教室之间的距离
//                classroomDistance(constraintFactory),
//                timeDistribution(constraintFactory)
        };
    }

//    private Constraint courseInterval(ConstraintFactory constraintFactory) {
//        return constraintFactory.forEachUniquePair(TeachingSession.class,
//                        Joiners.equal(TeachingSession::getOfferingId),
//                        Joiners.lessThan(TeachingSession::getTimeslotId))
//                .filter((session1, session2) -> {
//                    long daysBetween = ChronoUnit.DAYS.between(
//                            session1.getTimeslot().getDate(),
//                            session2.getTimeslot().getDate());
//                    return daysBetween < 2; // 同一课程间隔应至少2天
//                })
//                .penalize(HardSoftScore.ONE_SOFT)
//                .asConstraint("课程间隔过近");
//
//    }

    private Constraint teacherTimePreference(ConstraintFactory constraintFactory) {
        // 教师不可排课时间
        return constraintFactory.forEach(TeachingSession.class)
                .filter(session -> !session.getTimeslots()
                        .contains(session.getTimeslot()))
                .penalize(HardSoftScore.ONE_SOFT)
                .asConstraint("Teacher TimePreference");
    }

    private Constraint classroomCapacity(ConstraintFactory constraintFactory) {
        // 课程不能超过教室容量
        return constraintFactory.forEach(TeachingSession.class)
                .filter(session -> {
                    if (session.getClassroom() == null ||
                            session.getOfferingId() == null) {
                        return false;
                    }
                    return session.getClassroom().getCapacity() <
                            session.getStudentCount();
                })
                .penalize(HardSoftScore.ONE_HARD,
                        session -> session.getStudentCount() -
                                session.getClassroom().getCapacity())
                .asConstraint("Classroom capacity");
    }

    private Constraint classroomConflict(ConstraintFactory constraintFactory) {
        // 一个教室同时最多上一节课
        return constraintFactory.forEach(TeachingSession.class)
                .join(TeachingSession.class,
                        Joiners.equal(TeachingSession::getTimeslot),
                        Joiners.equal(TeachingSession::getClassroom),
                        Joiners.lessThan(TeachingSession::getId))
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Classroom conflict");
    }

    private Constraint teacherConflict(ConstraintFactory constraintFactory) {
        // 一个老师同时最多上一节课
        return constraintFactory.forEach(TeachingSession.class)
                .join(TeachingSession.class,
                        Joiners.equal(TeachingSession::getTimeslot),
                        Joiners.equal(TeachingSession::getOfferingId),
                        Joiners.lessThan(TeachingSession::getId))
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Teacher conflict");
    }
}
