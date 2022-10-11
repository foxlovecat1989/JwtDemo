package com.example.jwtdemo.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface JwtService {
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
