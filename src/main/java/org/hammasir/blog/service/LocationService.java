package org.hammasir.blog.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hammasir.blog.dto.PostLocationDto;
import org.hammasir.blog.entity.Post;
import org.hammasir.blog.entity.State;
import org.hammasir.blog.projection.PostInfo;
import org.hammasir.blog.repository.StateRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LocationService {
    StateRepository stateRepository;

    State getStateOfPost(Post post) {
        return stateRepository.findStateByLocation(post.getLocation());
    }



    public List<PostInfo> findPostByState(String state) {
        var posts = stateRepository.findPostByState(state);
        return posts;
    }
}
