package org.hammasir.blog.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hammasir.blog.entity.Post;
import org.hammasir.blog.entity.User;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private UserDto author;

    public  PostDto(Post post){
        this.id= post.getId();
        this.title= post.getTitle();
        this.content= post.getContent();
        User author =post.getAuthor();
        this.author=UserDto.builder()
                .name(author.getName())
                .username(author.getUsername())
                .build();
    }
}