package com.verse.app.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.verse.app.entity.UserInfo;
import com.verse.common.util.JwtTokenUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("token");
        if(token == null) {
            filterChain.doFilter(request, response);
        }
        DecodedJWT decodedJWT = JwtTokenUtils.verify(token);
        String username = decodedJWT.getClaim("username").asString();
        //查询redis
        String subject = decodedJWT.getSubject();
        UserInfo userInfo = new UserInfo();
        userInfo.setUserPhone("caicai");
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(new UserInfo(), null);

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        filterChain.doFilter(request, response);
    }

}
