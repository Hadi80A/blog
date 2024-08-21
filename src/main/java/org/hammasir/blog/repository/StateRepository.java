package org.hammasir.blog.repository;

import org.hammasir.blog.dto.PostLocationDto;
import org.hammasir.blog.entity.Post;
import org.hammasir.blog.entity.State;
import org.hammasir.blog.projection.PostInfo;
import org.locationtech.jts.geom.Geometry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StateRepository extends JpaRepository<State, Integer>{

    @Query(value = "SELECT * FROM iran_states WHERE st_contains(geom, :point)",nativeQuery = true)
    State findStateByLocation(@Param("point") Geometry p);

    @Query(value = """
            SELECT *
            FROM iran_states, post
            WHERE ST_Contains(geom, post.location)
            AND iran_states.name = :state
            """,
            nativeQuery = true)
    List<PostInfo>  findPostByState(@Param("state") String state );
}