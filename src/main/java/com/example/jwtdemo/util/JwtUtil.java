package com.example.jwtdemo.util;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class JwtUtil {
    public static String ALGORITHM_KEY;

    @Value("${com.example.jwtdemo.jwt.algorithmKey}")
    public void setAlgorithmKey(String algorithmKey) {
        ALGORITHM_KEY = algorithmKey;
    }

    public static Algorithm getAlgorithm(){
        return Algorithm.HMAC256(ALGORITHM_KEY.getBytes(StandardCharsets.UTF_8));
    }
}
