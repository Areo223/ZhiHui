package org.areo.zhihui.interceptor;

import com.alibaba.fastjson.JSONObject;
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
        log.info("请求的url为:{}",url);


        String token = request.getHeader("Authorization");

        if (!StringUtils.hasLength(token)){
            log.info("请求头token为空,返回未登录信息");

            response.getWriter().write(Restful.unauthorized("未登录").toJsonString());
            return false;
        }

        try {
            // 去除可能的前缀（如"Bearer "）
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            jwtUtils.verifyToken(token);
        }catch (JwtErrorException e){
            e.printStackTrace();
            log.info("解析令牌失败,返回未登录错误信息");


            String notLogin = JSONObject.toJSONString(Restful.unauthorized("未登录").toJsonString());
            response.getWriter().write(notLogin);
            return false;
        }

        log.info("令牌合法,放行");


        User user = (jwtUtils.parseToken(token));
        UserHolder.setUser(userMapper.selectById(user.getId()));//设置线程局部变量user
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {
        UserHolder.removeUser();
    }

    /**
     * Token验证逻辑（返回Result）
     */
    private Result<User> checkToken(String token) {
        // 1. 检查Token是否存在
        if (!StringUtils.hasLength(token)) {
            return Result.failure(
                    new UnauthorizedException("未提供有效的Token"),
                    Restful.UNAUTHORIZED_CODE
            );
        }

        // 2. 解析Token
        try {
            String jwtToken = token.substring(7).trim();
            User user = jwtUtils.parseToken(jwtToken);
            UserHolder.setUser(userMapper.selectById(user.getId()));
            log.info("用户 {} 认证通过", user.getName());
            return Result.success(user);
        } catch (UnauthorizedException e) {
            return Result.failure(e, Restful.UNAUTHORIZED_CODE);
        }
    }

    /**
     * 发送错误响应（使用Result格式）
     */
    private void sendErrorResponse(HttpServletResponse response,
                                   Throwable error, int code) throws IOException {
        response.setStatus(code);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(
                Result.failure(error, code).toJson().toJsonString()
        );
    }
}