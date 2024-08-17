package org.hammasir.blog.service;

import org.hammasir.blog.dto.RegisterUserDto;
import org.hammasir.blog.dto.UserInfoDto;
import org.hammasir.blog.entity.Role;
import org.hammasir.blog.entity.User;
import org.hammasir.blog.entity.UserPassword;
import org.hammasir.blog.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User add(RegisterUserDto userRequest, Role role){
        String encryptedPass=passwordEncoder.encode(userRequest.password());
        UserPassword userPassword = UserPassword.builder()
                .password(encryptedPass)
                .build();

        User user=User.builder()
                .name(userRequest.name())
                .username(userRequest.username())
                .password(userPassword)
                .role(role)
                .build();
        return userRepository.save(user);
    }


    public List<UserInfoDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserInfoDto::new)
                .collect(Collectors.toList());
    }
}