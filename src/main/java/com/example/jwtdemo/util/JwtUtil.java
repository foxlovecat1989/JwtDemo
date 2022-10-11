package com.example.jwtdemo.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static com.example.jwtdemo.filter.CustomerAuthenticationFilter.ONE_DAY_MILLISECONDS;
import static com.example.jwtdemo.filter.CustomerAuthenticationFilter.TEN_MINUTES_MILLISECONDS;

@Component
public class JwtUtil {
    public static String ALGORITHM_KEY;
    private static Algorithm algorithm;

    @Value("${com.example.jwtdemo.jwt.algorithmKey}")
    public void setAlgorithmKey(String algorithmKey) {
        JwtUtil.ALGORITHM_KEY = algorithmKey;
    }

    @PostConstruct
    private static void init() {
        algorithm = getAlgorithm();
    }

    public static Algorithm getAlgorithm(){
        return Algorithm.HMAC256(ALGORITHM_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public static String getAccessToken(HttpServletRequest request, String subject, String[] roles) {
        return JWT.create()
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + TEN_MINUTES_MILLISECONDS))
                .withIssuer(request.getRequestURI())
                .withArrayClaim("roles", roles)
                .sign(algorithm);
    }

    public static String getRefreshToken(HttpServletRequest request, User user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + ONE_DAY_MILLISECONDS))
                .withIssuer(request.getRequestURI())
                .sign(algorithm);
    }
}
