package org.areo.zhihui.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import org.areo.zhihui.exception.JwtErrorException;
import org.areo.zhihui.pojo.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {

    @Value("${jwt.secret}") // 默认密钥
    private String secret;

    @Value("${jwt.expire:86400000}") // 默认1天(毫秒)
    private long expire;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        // 将字符串密钥转换为安全的SecretKey
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 生成JWT令牌
     */
    public String generateToken(User user) throws JwtErrorException {
        try {
            Map<String, Object> claims = new HashMap<>();
            claims.put("uid", user.getId());
            claims.put("ident", user.getIdentifier());

            return Jwts.builder()
                    .claims(claims)
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + expire))
                    .signWith(secretKey, Jwts.SIG.HS256)
                    .compact();
        } catch (Exception e) {
            throw new JwtErrorException("生成Token失败: " + e.getMessage());
        }
    }

    /**
     * 验证JWT令牌
     */
    public boolean verifyToken(String token) throws JwtErrorException {
        try {
            parseToken(token); // 复用解析逻辑
            return true;
        } catch (JwtErrorException e) {
            return false;
        }
    }

    /**
     * 解析JWT令牌
     */
    public User parseToken(String token) throws JwtErrorException {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            User user = new User();
            user.setId(claims.get("uid", Integer.class));
            user.setIdentifier(claims.get("ident", String.class));
            return user;

        } catch (ExpiredJwtException e) {
            throw new JwtErrorException("Token已过期");
        } catch (SignatureException e) {
            throw new JwtErrorException("Token签名无效");
        } catch (MalformedJwtException e) {
            throw new JwtErrorException("Token格式错误");
        } catch (JwtException e) {
            throw new JwtErrorException("Token解析失败: " + e.getMessage());
        }
    }
}