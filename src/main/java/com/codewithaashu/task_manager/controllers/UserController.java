
package com.codewithaashu.task_manager.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithaashu.task_manager.Payload.ApiResponse;
import com.codewithaashu.task_manager.Payload.ApiResponseWithoutData;
import com.codewithaashu.task_manager.Payload.ApiResponses;
import com.codewithaashu.task_manager.Payload.UserDto;
import com.codewithaashu.task_manager.service.implementation.UserServiceImpl;

@RestController
@RequestMapping("/api/v1/user")

public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @PatchMapping("/change-password")
    public ResponseEntity<ApiResponseWithoutData> changePasswordController(@RequestBody UserDto userDto) {
        // send to service
        userServiceImpl.changePassword(userDto.getEmail(), userDto.getPassword());
        return new ResponseEntity<ApiResponseWithoutData>(
                new ApiResponseWithoutData("Password changed successfully", true), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponseWithoutData> activateUserProfileController(@PathVariable("id") Long id) {
        // send to service
        Boolean result = userServiceImpl.activateUserProfile(id);
        String message = String.format("User account has been %s successfully", result ? "activated" : "disabled");
        return new ResponseEntity<ApiResponseWithoutData>(
                new ApiResponseWithoutData(message, true), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseWithoutData> deleteUserController(@PathVariable("id") Long id) {
        // send to service
        userServiceImpl.deleteUser(id);
        return new ResponseEntity<ApiResponseWithoutData>(
                new ApiResponseWithoutData("User deleted successfully", true), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> updateUserController(@RequestBody UserDto userDto,
            @PathVariable Long id) {
        // send to the service
        UserDto updateUserDto = userServiceImpl.updateUser(userDto, id);
        updateUserDto.setPassword(null);
        return new ResponseEntity<>(new ApiResponse<UserDto>(updateUserDto, "Profile Updated Successfully", true),
                HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponses<UserDto>> getTeamListController() {
        // send to service
        List<UserDto> userDtos = userServiceImpl.getTeamList();
        return new ResponseEntity<>(new ApiResponses<>(userDtos, "fetched successfully", true), HttpStatus.OK);
    }

}