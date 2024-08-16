package org.hammasir.blog.controller;


import org.hammasir.blog.dto.LoginResponseDto;
import org.hammasir.blog.dto.LoginUserDto;
import org.hammasir.blog.dto.RegisterUserDto;
import org.hammasir.blog.entity.User;
import org.hammasir.blog.service.AuthenticationService;
import org.hammasir.blog.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    
    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        long expirationTime = jwtService.getExpirationTime();
        LoginResponseDto loginResponse = new LoginResponseDto(jwtToken,expirationTime);

        return ResponseEntity.ok(loginResponse);
    }
}