package org.hammasir.blog.repository;


import org.hammasir.blog.entity.Post;
import org.hammasir.blog.entity.User;
import org.hammasir.blog.projection.PostProjection;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
    List<Post> findByAuthor(User author);

    List<Post> findByTitleContaining(String keyword);

    @Query(value = "SELECT p.* FROM post p WHERE ST_DWithin(p.location, :point, :distance)",nativeQuery = true)
    List<Post> findNearbyPosts(@Param("point") Point point, @Param("distance") double distance);

}