package com.codewithaashu.task_manager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithaashu.task_manager.Payload.ApiResponse;
import com.codewithaashu.task_manager.Payload.ApiResponseWithoutData;
import com.codewithaashu.task_manager.Payload.LoginUserRequest;
import com.codewithaashu.task_manager.Payload.UserDto;
import com.codewithaashu.task_manager.service.implementation.UserServiceImpl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private UserServiceImpl userServiceImpl;

    // mapping the controller
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDto>> registerUserController(@Valid @RequestBody UserDto userDto) {
        // send to the service
        UserDto savedUserDto = userServiceImpl.registerUser(userDto);
        savedUserDto.setPassword(null);
        return new ResponseEntity<>(new ApiResponse<UserDto>(savedUserDto, "Registered successfully", true),
                HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseWithoutData> loginUserContoller(@Valid @RequestBody LoginUserRequest user,
            HttpServletResponse response) {

        String result = userServiceImpl.loginUser(user.getEmail(), user.getPassword());
        if (!result.equals("FAILED")) {
            // set cookie
            // create an object of cookie
            Cookie cookie = new Cookie("token", result);
            cookie.setMaxAge(7 * 24 * 60 * 60);
            cookie.setSecure(true);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            // add cookie in response
            response.addCookie(cookie);

        }
        return new ResponseEntity<>(new ApiResponseWithoutData("Login Successfully", true), HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity<ApiResponseWithoutData> logoutUserContoller(
            HttpServletResponse response) {
        // set cookie
        // create an object of cookie
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        // add cookie in response
        response.addCookie(cookie);
        return new ResponseEntity<>(new ApiResponseWithoutData("Logout Successfully", true), HttpStatus.OK);
    }
}
