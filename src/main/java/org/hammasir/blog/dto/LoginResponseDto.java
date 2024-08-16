package org.hammasir.blog.dto;

public record LoginResponseDto(String token,long expiresIn) {
}
