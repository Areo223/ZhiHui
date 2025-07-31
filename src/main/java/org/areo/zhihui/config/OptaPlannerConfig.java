package org.areo.zhihui.config;

import org.areo.zhihui.pojo.dto.TimeTable;
import org.areo.zhihui.services.TimeTableConstraintProvider;
import org.optaplanner.core.api.solver.SolverManager;
import org.optaplanner.core.config.solver.SolverConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class OptaPlannerConfig {

    @Bean
    public SolverManager<TimeTable, UUID> solverManager() {
        SolverConfig solverConfig = SolverConfig.createFromXmlResource(
                "solverConfig.xml");

        // 确保配置了约束提供类（二选一）
        // 方式1：使用XML配置（在solverConfig.xml中配置）
        // 方式2：使用编程式配置：
        solverConfig.withConstraintProviderClass(TimeTableConstraintProvider.class);
        // 或者：
        // solverConfig.withEasyScoreCalculatorClass(TimeTableEasyScoreCalculator.class);
        // 或者：
        // solverConfig.withIncrementalScoreCalculatorClass(TimeTableIncrementalScoreCalculator.class);

        return SolverManager.create(solverConfig);
    }
}
