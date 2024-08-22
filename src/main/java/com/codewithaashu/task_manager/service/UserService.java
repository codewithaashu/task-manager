package com.codewithaashu.task_manager.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.codewithaashu.task_manager.Payload.UserDto;

@Service
public interface UserService {
    UserDto registerUser(UserDto userDto);

    void changePassword(String email, String paasword);

    Boolean activateUserProfile(Long id);

    void deleteUser(Long id);

    UserDto updateUser(UserDto userDto, Long id);

    List<UserDto> getTeamList();
}
