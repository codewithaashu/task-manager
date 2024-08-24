package com.codewithaashu.task_manager.utils;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codewithaashu.task_manager.Entity.User;
import com.codewithaashu.task_manager.repository.UserRepository;
import com.codewithaashu.task_manager.security.JWTService;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class MiddlewareUtils {
    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserRepository userRepository;

    public User getLoggedInUser(HttpServletRequest request) {
        // get cookies
        Cookie[] cookies = request.getCookies();
        String token;
        if (cookies != null) {
            token = Arrays.stream(cookies).map(cookie -> {
                if (cookie.getName().equals("token")) {
                    return cookie.getValue();
                }
                return null;
            }).collect(Collectors.joining(""));
        } else {
            throw new JwtException("Token is invalid.Please login again");
        }
        // get username from it
        String username = jwtService.extractUserName(token);
        // find userdetails
        User user = userRepository.findByEmail(username);
        return user;
    }

}
