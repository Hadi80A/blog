package org.hammasir.blog.service;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hammasir.blog.dto.UserDto;
import org.hammasir.blog.entity.Role;
import org.hammasir.blog.entity.User;
import org.hammasir.blog.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;
    AuthenticationManager authenticationManager;
    UserService userService;
    LoggerService loggerService;

    public User signup(UserDto input) {
        input.setRole(Role.USER);
        User user =userService.add(input);
        return user;
    }

    public User authenticate(UserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsername(),
                        input.getPassword()
                )
        );

        loggerService.send("user " + input.getUsername() + "send login request");

        return userRepository.findByUsername(input.getUsername())
                .orElseThrow(); //todo new ResourceNotFoundException
    }
}