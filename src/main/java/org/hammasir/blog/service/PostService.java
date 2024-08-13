package org.hammasir.blog.service;

import org.hammasir.blog.entity.Post;
import org.hammasir.blog.entity.User;
import org.hammasir.blog.controller.advice.*;
import org.hammasir.blog.repository.PostRepository;
import org.hammasir.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Post not found with id " + id));
    }

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public Post updatePost(Long id, Post postDetails) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Post not found with id " + id));
        
        post.setTitle(postDetails.getTitle());
        post.setContent(postDetails.getContent());
        post.setAuthor(postDetails.getAuthor());
        return postRepository.save(post);
    }

    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Post not found with id " + id));
        
        postRepository.deleteById(id);
    }

    public List<Post> getPostsByAuthor(Long authorId) {
        User author = userRepository.findById(authorId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + authorId));
        
        return postRepository.findByAuthor(author);
    }

    public List<Post> getPostsByKeyword(String keyword) {
        return postRepository.findByTitleContaining(keyword);
    }
}
