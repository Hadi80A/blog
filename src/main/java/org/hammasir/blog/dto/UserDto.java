package org.hammasir.blog.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hammasir.blog.entity.Role;
import org.hammasir.blog.entity.User;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private Long id;
    private String name;
    private String username;
    private String password;
    private Role role;
    private List<PostDto> posts;

    public UserDto(User user) {
        this.id= user.getId();
        this.name=user.getName();
        this.username=getUsername();
        this.posts= user.getPosts().stream()
                .map(PostDto::new)
                .collect(Collectors.toList());

    }
}
