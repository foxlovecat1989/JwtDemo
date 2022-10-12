package com.example.jwtdemo.filter;

import com.example.jwtdemo.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Slf4j
public class CustomerAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    public static final int TEN_MINUTES_MILLISECONDS = 1 * 60 * 1000;
    public static final int ONE_DAY_MILLISECONDS = 24 * 60 * 60 * 1000;
    private final AuthenticationManager authenticationManager;

    public CustomerAuthenticationFilter(
            AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        var userName = request.getParameter("username");
        var password = request.getParameter("password");
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(userName, password);

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authentication) throws IOException {
        var user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        var algorithm = JwtUtil.getAlgorithm();
        var roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toArray(String[]::new);
        var accessToken = JwtUtil.getAccessToken(request, user.getUsername(), roles);
        var refreshToken = JwtUtil.getRefreshToken(request, user);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }
}
