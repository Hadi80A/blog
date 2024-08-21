package org.hammasir.blog.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hammasir.blog.dto.*;
import org.hammasir.blog.entity.Post;
import org.hammasir.blog.projection.PostInfo;
import org.hammasir.blog.projection.PostProjection;
import org.hammasir.blog.service.LocationService;
import org.hammasir.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class PostController {

    PostService postService;
    LocationService locationService;



    @GetMapping("${app.urls.post.all}")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> posts = postService.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PostMapping("${app.urls.post.add}")
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        Post createdPost = postService.createPost(post);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post postDetails) {
        Post updatedPost = postService.updatePost(id, postDetails);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/author/{username}")
    public ResponseEntity<List<PostDto>> getPostsByAuthor(@PathVariable String username) {
        List<PostDto> posts = postService.getPostsByAuthor(username);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("${app.urls.post.search}")
    public ResponseEntity<List<PostDto>> getPostsByKeyword(@RequestParam String keyword) {
        List<PostDto> posts = postService.getPostsByKeyword(keyword);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<PostDto>> filterPosts(@ModelAttribute FilterParamsDto filterParamsDto){
        var posts = postService.findAll(filterParamsDto);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<PostDto>> getNearbyPosts(@ModelAttribute NearbyRequestDto nearbyRequestDto){
        var response= postService.findNearbyPosts(nearbyRequestDto);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/location/{id}")
    public ResponseEntity<PostLocationDto> getLocationByPostId(@PathVariable Long id){
        var post=postService.getStateByPostId(id);
        return new ResponseEntity<> (post,HttpStatus.OK);
    }

    @PostMapping("/location")
    public ResponseEntity<List<PostInfo>> getPostByState(@RequestBody StateRequestDto req){
        var posts=locationService.findPostByState(req.state());
        return new ResponseEntity<> (posts,HttpStatus.OK);
    }


}
