package org.hammasir.blog.service;

import org.hammasir.blog.dto.UserDto;
import org.hammasir.blog.entity.User;
import org.hammasir.blog.entity.UserPassword;
import org.hammasir.blog.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

    public User add(UserDto userRequest){
        String encryptedPass=passwordEncoder.encode(userRequest.getPassword());
        UserPassword userPassword = UserPassword.builder()
                .password(encryptedPass)
                .build();

        User user=User.builder()
                .name(userRequest.getName())
                .username(userRequest.getUsername())
                .password(userPassword)
                .role(userRequest.getRole())
                .build();
        return userRepository.save(user);
    }


    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserDto::new)
                .collect(Collectors.toList());
    }
}