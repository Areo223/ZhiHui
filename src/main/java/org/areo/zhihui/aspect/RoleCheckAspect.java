package org.areo.zhihui.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.areo.zhihui.annotation.RequiresRole;
import org.areo.zhihui.exception.UserException.UserAccessDeniedException;
import org.areo.zhihui.utils.UserHolder;
import org.areo.zhihui.utils.enums.RoleEnum;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleCheckAspect {

    @Around("@annotation(requiresRole)") // 拦截带有 @RequiresRole 注解的方法
    public Object checkRole(ProceedingJoinPoint joinPoint, RequiresRole requiresRole) throws Throwable {
        RoleEnum role = UserHolder.getUser().getRole();
        if (Arrays.asList(requiresRole.value()).contains(role)) {
            return joinPoint.proceed(); // 放行，执行原方法
        } else {
            log.error("权限不足，需要角色: {}", Arrays.toString(requiresRole.value()));
            throw new UserAccessDeniedException("权限不足，需要角色: " + Arrays.toString(requiresRole.value()));

        }
    }

}
