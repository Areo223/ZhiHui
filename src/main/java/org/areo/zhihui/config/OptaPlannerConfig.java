package org.areo.zhihui.config;

import org.areo.zhihui.pojo.dto.TimeTable;
import org.areo.zhihui.pojo.entity.TeachingSession;
import org.areo.zhihui.services.TimeTableConstraintProvider;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.api.solver.SolverManager;
import org.optaplanner.core.config.solver.SolverConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.UUID;

@Configuration
// 开启自动配置
@EnableAutoConfiguration
public class OptaPlannerConfig {

    @Bean
    public SolverManager<TimeTable, UUID> solverManager(SolverConfig solverConfig) {
        return SolverManager.create(solverConfig);
    }

//    @Bean
//    public SolutionManager<TimeTable, HardSoftScore> solutionManager(
//            SolverFactory<TimeTable> solverFactory) {
//        return SolutionManager.create(solverFactory);
//    }

    @Bean
    public SolverFactory<TimeTable> solverFactory(SolverConfig solverConfig) {
        return SolverFactory.create(solverConfig);
    }

    @Bean
    public SolverConfig solverConfig() {
        return new SolverConfig()
                // 定义解决方案类
                .withSolutionClass(TimeTable.class)
                // 定义实体类
                .withEntityClasses(TeachingSession.class)
                // 定义约束提供者类
                .withConstraintProviderClass(TimeTableConstraintProvider.class)
                // 定义求解时间限制
                .withTerminationSpentLimit(Duration.ofMinutes(5));
    }

}
