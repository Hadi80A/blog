package org.hammasir.blog.controller;

import org.hammasir.blog.dto.UserDto;
import org.hammasir.blog.entity.User;
import org.hammasir.blog.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/users")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("${app.urls.users.current-user}")
    public ResponseEntity<UserDto> getAuthenticatedUser(@AuthenticationPrincipal User currentUser) {
        UserDto userDto=UserDto.builder()
                .name(currentUser.getName())
                .username(currentUser.getUsername())
                .role(currentUser.getRole())
                .build();
        return ResponseEntity.ok(userDto);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
