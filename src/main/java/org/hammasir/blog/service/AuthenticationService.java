package org.hammasir.blog.service;


import org.hammasir.blog.controller.advice.ResourceNotFoundException;
import org.hammasir.blog.dto.RegisterUserDto;
import org.hammasir.blog.dto.LoginUserDto;
import org.hammasir.blog.entity.Role;
import org.hammasir.blog.entity.User;
import org.hammasir.blog.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
        UserRepository userRepository,
        AuthenticationManager authenticationManager,
        PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(RegisterUserDto input) {
        User user = User.builder()
                .name(input.name())
                .username(input.username())
//                .password(passwordEncoder.encode(input.password()))
                .role(Role.USER)
                .build();

        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.username(),
                        input.password()
                )
        );

        return userRepository.findByUsername(input.username())
                .orElseThrow(); //todo new ResourceNotFoundException
    }
}