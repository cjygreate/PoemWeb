package com.verse.app.filter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.verse.app.entity.UserInfo;
import com.verse.common.util.JwtTokenUtils;
import com.verse.common.util.RedisUtils;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("token");
        if(token != null ) {
            DecodedJWT decodedJWT = JwtTokenUtils.verify(token);
            String username = decodedJWT.getClaim("username").asString();
            String user = redisUtils.get("user_" + username);
            //校验是否过期
            if(StringUtils.isEmpty(user) || decodedJWT.getExpiresAt().before(new Date())) {
                throw new RuntimeException("登录过期，请重新登录");
            }
            String subject = decodedJWT.getSubject();

            UserInfo userInfo = JSONArray.parseObject(user, UserInfo.class);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userInfo, null, null);
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        filterChain.doFilter(request, response);
    }

}
