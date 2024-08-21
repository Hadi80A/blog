package org.hammasir.blog.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String title;
    String content;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    User author;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(nullable = false)
    LocalDateTime updatedDate;

    @Builder.Default
    int likes = 0;

    @Column(name = "location",columnDefinition = "geometry(Point, 4326)")
    Geometry location;

}
