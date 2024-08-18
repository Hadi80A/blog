package org.hammasir.blog.controller;

import org.hammasir.blog.dto.LoginResponseDto;
import org.hammasir.blog.dto.UserDto;
import org.hammasir.blog.entity.User;
import org.hammasir.blog.service.AuthenticationService;
import org.hammasir.blog.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("${app.urls.auth.base}")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("${app.urls.auth.signup}")
    public ResponseEntity<UserDto> register(@RequestBody UserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);


        UserDto userDto = UserDto.builder()
                .id(registeredUser.getId())
                .name(registeredUser.getName())
                .username(registeredUser.getUsername())
                .build();

        return ResponseEntity.ok(userDto);
    }

    @PostMapping("${app.urls.auth.login}")
    public ResponseEntity<LoginResponseDto> authenticate(@RequestBody UserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);
        long expirationTime = jwtService.getExpirationTime();

        LoginResponseDto loginResponse = new LoginResponseDto(jwtToken, expirationTime);
        return ResponseEntity.ok(loginResponse);
    }
}
