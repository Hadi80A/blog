package org.hammasir.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hammasir.blog.entity.Post;

@Data
@AllArgsConstructor
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private PostAuthorDto author;

    public  PostDto(Post post){
        this.id= post.getId();
        this.title= post.getTitle();
        this.content= post.getContent();
        this.author=new PostAuthorDto(
                post.getAuthor().getId(),
                post.getAuthor().getName()
        );
    }
}
record PostAuthorDto(Long id, String name) {
}