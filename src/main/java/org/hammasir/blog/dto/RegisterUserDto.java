package org.hammasir.blog.dto;

public record RegisterUserDto(
        String name,
        String username,
        String password
) {
}
