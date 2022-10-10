package com.example.jwtdemo.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
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
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Slf4j
public class CustomerAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    public static final int TEN_MINUTES_MILLISECONDS = 10 * 60 * 1000;
    public static final int ONE_DAY_MILLISECONDS = 24 * 60 * 60 * 1000;
    public static final String APPLICATION_JSON_VALUE = "application/json";
    private final String algorithmKey;
    private final AuthenticationManager authenticationManager;

    public CustomerAuthenticationFilter(
            AuthenticationManager authenticationManager,
            String algorithmKey) {
        this.algorithmKey = algorithmKey;
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
        var algorithm = Algorithm.HMAC256(algorithmKey.getBytes(StandardCharsets.UTF_8));
        var roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toArray(String[]::new);

        var accessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + TEN_MINUTES_MILLISECONDS))
                .withIssuer(request.getRequestURI())
                .withArrayClaim("roles", roles)
                .sign(algorithm);

        var refreshToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + ONE_DAY_MILLISECONDS))
                .withIssuer(request.getRequestURI())
                .sign(algorithm);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);

        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }
}
