package org.hammasir.blog.dto;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.hammasir.blog.entity.State;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;

import java.io.Serializable;

/**
 * DTO for {@link org.hammasir.blog.entity.Post} with {@link org.hammasir.blog.entity.State}
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostLocationDto{
    String title;
    String authorName;
    Geometry location;
    String state;
}