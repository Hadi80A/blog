package org.hammasir.blog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String content;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;
}
