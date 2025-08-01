package org.areo.zhihui.config;

import org.areo.zhihui.pojo.dto.TimeTable;
import org.areo.zhihui.pojo.entity.TeachingSession;
import org.areo.zhihui.services.TimeTableConstraintProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.solver.SolutionManager;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.api.solver.SolverManager;
import org.optaplanner.core.config.solver.SolverConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.UUID;

@Configuration
@EnableAutoConfiguration
public class OptaPlannerConfig {

    @Bean
    public SolverManager<TimeTable, UUID> solverManager(SolverConfig solverConfig) {
        return SolverManager.create(solverConfig);
    }

    @Bean
    public SolutionManager<TimeTable, HardSoftScore> solutionManager(
            SolverFactory<TimeTable> solverFactory) {
        return SolutionManager.create(solverFactory);
    }

    @Bean
    public SolverFactory<TimeTable> solverFactory(SolverConfig solverConfig) {
        return SolverFactory.create(solverConfig);
    }

    @Bean
    public SolverConfig solverConfig() {
        return new SolverConfig()
                .withSolutionClass(TimeTable.class)
                .withEntityClasses(TeachingSession.class)
                .withConstraintProviderClass(TimeTableConstraintProvider.class)
                .withTerminationSpentLimit(Duration.ofMinutes(1));
    }

}
