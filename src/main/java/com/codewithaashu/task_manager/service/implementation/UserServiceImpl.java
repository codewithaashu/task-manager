package com.codewithaashu.task_manager.service.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.codewithaashu.task_manager.Entity.User;
import com.codewithaashu.task_manager.Payload.UserDto;
import com.codewithaashu.task_manager.exceptions.ResourceNotFoundException;
import com.codewithaashu.task_manager.repository.UserRepository;
import com.codewithaashu.task_manager.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    // create an instance of userRepository
    @Autowired
    private UserRepository userRepository;

    // for converting one class to another class
    @Autowired
    private ModelMapper modelMapper;

    // for encryption the password
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    @Override
    public UserDto registerUser(UserDto userDto) {
        // save in repository,but repository only takes entity type value. so we have to
        // change
        User user = modelMapper.map(userDto, User.class);
        // encrypt the password
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        // save in repository
        User savedUser = userRepository.save(user);
        // map the saved user to userDto
        UserDto savedUserDto = modelMapper.map(savedUser, UserDto.class);
        return savedUserDto;
    }

    @Override
    public void changePassword(String email, String password) {
        // find user by email
        User user = userRepository.findByEmail(email);
        // if we do not get user
        if (user == null) {
            throw new ResourceNotFoundException("User", "Email", email);
        }
        // change the password
        user.setPassword(bCryptPasswordEncoder.encode(password));
        // save in repository
        userRepository.save(user);
    }

    @Override
    public Boolean activateUserProfile(Long id) {
        // find user by id
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id.toString()));
        // if we get user, change the isActive field
        user.setIsActive(!user.getIsActive());
        // save in repository
        userRepository.save(user);
        return user.getIsActive();
    }

    @Override
    public void deleteUser(Long id) {
        // find user by id
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id.toString()));
        // if we get user, then delete
        userRepository.delete(user);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long id) {
        System.out.println(userDto);
        // find user by id
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id.toString()));
        // if user exist then set data of user with new user data
        user.setName(userDto.getName() == null ? user.getName() : userDto.getName());
        user.setTitle(userDto.getTitle() == null ? user.getTitle() : userDto.getTitle());
        user.setIsAdmin(userDto.getIsAdmin() == null ? user.getIsAdmin() : userDto.getIsAdmin());
        user.setRole(userDto.getRole() == null ? user.getRole() : userDto.getRole());
        // save in repository
        User updatedUser = userRepository.save(user);
        // change in modal form
        return modelMapper.map(updatedUser, UserDto.class);
    }

    @Override
    public List<UserDto> getTeamList() {
        // get all users
        List<User> users = userRepository.findAll();
        // convert in userDto
        List<UserDto> userDtos = users.stream().map(user -> {
            user.setPassword(null);
            return modelMapper.map(user, UserDto.class);
        }).collect(Collectors.toList());
        return userDtos;
    }
}
