package org.hammasir.blog.projection;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;


/**
 * Projection for {@link org.hammasir.blog.entity.Post}
 */
public interface PostInfo {
    Long getId();
    String getTitle();

    int getLikes();

    Geometry getLocation();

//    UserInfo getAuthor();
//
//    /**
//     * Projection for {@link org.hammasir.blog.entity.User}
//     */
//    interface UserInfo {
//        String getName();
//
//        String getUsername();
//    }
}