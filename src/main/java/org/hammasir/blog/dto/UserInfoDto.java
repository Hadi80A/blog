package org.hammasir.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hammasir.blog.entity.User;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class UserInfoDto {
    private Long id;
    private String name;
    private String username;
    private List<PostDto> posts;
    public UserInfoDto(User user){
        this.id= user.getId();
        this.name=user.getName();
        this.username=getUsername();
        this.posts= user.getPosts().stream()
                .map(PostDto::new)
                .collect(Collectors.toList());
    }
}
