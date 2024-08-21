package org.hammasir.blog.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hammasir.blog.entity.Post;
import org.hammasir.blog.entity.User;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private int likes;
    private UserDto author;
    private Geometry location;


    public  PostDto(Post post){
        this.id= post.getId();
        this.title= post.getTitle();
        this.content= post.getContent();
        User author =post.getAuthor();
        this.likes= post.getLikes();
        this.location=post.getLocation();
        this.author=UserDto.builder()
                .name(author.getName())
                .username(author.getUsername())
                .build();
    }
}