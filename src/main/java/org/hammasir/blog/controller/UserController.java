package org.hammasir.blog.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hammasir.blog.dto.UserDto;
import org.hammasir.blog.entity.User;
import org.hammasir.blog.service.UserService;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class UserController {

    UserService userService;
    RedissonClient redison;

    @GetMapping("/test-redis")
    public String test(){
        RMap<String ,String > map= redison.getMap("test");
        map.put("123","hi");
        return map.get("123");
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
