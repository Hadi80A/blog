package org.hammasir.blog.projection;

import java.time.LocalDateTime;

public interface PostProjection {
    String getTitle();
    String getContent();
    int getLikes();
    LocalDateTime getCreatedDate();
    String getAuthorName();
    String getAuthorUsername();
}
