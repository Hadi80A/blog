package org.hammasir.blog.repository;


import org.hammasir.blog.entity.Post;
import org.hammasir.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAuthor(User author);
    List<Post> findByTitleContaining(String keyword);
}