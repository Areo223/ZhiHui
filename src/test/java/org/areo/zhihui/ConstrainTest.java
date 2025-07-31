//package org.areo.zhihui;
//
//import org.areo.zhihui.pojo.dto.TimeTable;
//import org.areo.zhihui.pojo.entity.Teacher;
//import org.areo.zhihui.pojo.entity.TeachingSession;
//import org.areo.zhihui.pojo.entity.Timeslot;
//import org.junit.jupiter.api.Test;
//import org.optaplanner.core.api.solver.SolverManager;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.util.List;
//import java.util.UUID;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.TimeUnit;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//@SpringBootTest
//public class ConstraintTest {
//
//    @Autowired
//    private SolverManager<TimeTable, UUID> solverManager;
//
//    @Test
//    public void testTeacherConflict() {
//        // 1. 准备测试数据
//        Teacher teacher = new Teacher();
//        teacher.setId(1);
//        teacher.setInformation("教授");
//
//        Timeslot timeslot = new Timeslot();
//        timeslot.setId(1);
//        timeslot.setStartTime(LocalTime.of(8, 0));
//        timeslot.setEndTime(LocalTime.of(9, 0));
//
//        TeachingSession session1 = new TeachingSession();
//        session1.setId(1);
//        session1.setTeacherId(1);
//        session1.setTimeslotId(1);
//
//        TeachingSession session2 = new TeachingSession();
//        session2.setId(2);
//        session2.setTeacherId(1);
//        session2.setTimeslotId(1);
//
//        TimeTable problem = new TimeTable(
//                List.of(timeslot),
//                List.of(session1, session2));
//
//// 2. 求解
//        try {
//            TimeTable solution = solverManager.solve(UUID.randomUUID(),
//                            problemBuilder -> problemBuilder,
//                            (problemId, solution) -> {})
//                    .getFinalBestSolution(10, TimeUnit.SECONDS);
//
//            // 3. 验证
//            assertThat(solution.getScore().hardScore()).isEqualTo(-1);
//        } catch (InterruptedException | ExecutionException e) {
//            Thread.currentThread().interrupt();
//            throw new RuntimeException("求解被中断", e);
//        }
//}