package org.areo.zhihui.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.areo.zhihui.exception.JwtErrorException;
import org.areo.zhihui.exception.UserException.UnauthorizedException;
import org.areo.zhihui.mapper.UserMapper;
import org.areo.zhihui.pojo.Restful;
import org.areo.zhihui.pojo.dto.Result;
import org.areo.zhihui.pojo.entity.User;
import org.areo.zhihui.utils.JwtUtils;
import org.areo.zhihui.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;



@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class LoginCheckInterceptor implements HandlerInterceptor {
    private final JwtUtils jwtUtils;
    private final UserMapper userMapper;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI();
        log.info("请求的url为: {}", url);

        // 获取并验证Token
        String token = request.getHeader("Authorization");
        Result<User> result = checkToken(token);

        // 根据验证结果处理
        if (result.isSuccess()) {
            log.info("令牌合法，用户 {} 已放行", UserHolder.getUserId());
            return true;
        } else {
            log.warn("拦截请求：{}，原因：{}", url, result.getError().getMessage());
            sendErrorResponse(response, result);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserHolder.removeUser();
    }

    private Result<User> checkToken(String token) {
        // 检查Token是否存在
        if (!StringUtils.hasLength(token)) {
            return Result.failure(new UnauthorizedException("未提供有效的Token"));
        }

        // 去除Bearer前缀
        String jwtToken = token;
        if (token.startsWith("Bearer ")) {
            jwtToken = token.substring(7).trim();
        }

        // 验证并解析Token
        try {
            User user = jwtUtils.parseToken(jwtToken);
            User dbUser = userMapper.selectById(user.getId());
            if (dbUser == null) {
                return Result.failure(new UnauthorizedException("用户不存在"));
            }

            // 将用户信息存入线程副本
            UserHolder.setUser(dbUser);
            return Result.success(null);
        } catch (JwtErrorException e) {
            return Result.failure(new UnauthorizedException("TOKEN验证失败: " + e.getMessage()));
        }
    }

    /**
     * 发送统一格式的错误响应
     */
    private void sendErrorResponse(HttpServletResponse response, Result<?> result) throws IOException {

        // 设置响应参数
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        // 构建统一返回结构
        Restful.ResultJson resultJson = Restful.unauthorized(result.getError().getMessage());

        // 写入响应
        response.getWriter().write(resultJson.toJsonString());
    }

}


