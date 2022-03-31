package com.vorobeyyyyyy.openchat.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vorobeyyyyyy.openchat.util.AuthUtils;
import com.vorobeyyyyyy.openchat.util.JwtUtils;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@AllArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private JwtUtils jwtUtil;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isEmpty(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        AuthInformation authInformation = jwtUtil.parseToken(token);
        AuthUtils.setAuth(JwtAuthenticationToken.of(authInformation));
        filterChain.doFilter(request, response);
    }
}
