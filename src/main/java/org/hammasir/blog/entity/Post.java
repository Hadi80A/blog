package org.hammasir.blog.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Post {
    @Id
    @GeneratedValue
    Long id;
    String title;
    String content;
    @ManyToOne
    @JoinColumn(name = "author_id")
    User author;
}
