package org.hammasir.blog.service;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hammasir.blog.component.PostCache;
import org.hammasir.blog.controller.advice.ResourceNotFoundException;
import org.hammasir.blog.dto.FilterParamsDto;
import org.hammasir.blog.dto.NearbyRequestDto;
import org.hammasir.blog.dto.PostDto;
import org.hammasir.blog.dto.PostLocationDto;
import org.hammasir.blog.dto.mapper.PostMapper;
import org.hammasir.blog.entity.Post;
import org.hammasir.blog.entity.State;
import org.hammasir.blog.entity.User;
import org.hammasir.blog.repository.PostRepository;
import org.hammasir.blog.repository.UserRepository;
import org.hammasir.blog.specification.PostSpecification;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cglib.core.internal.Function;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PostService {

    PostRepository postRepository;
    UserRepository userRepository;
    LocationService locationService;
    PostMapper postMapper;
    PostCache postCache;
    @Qualifier("taskExecutor")
    Executor taskExecutor;

    public List<PostDto> getAllPosts() {
        return postRepository.findAll().stream().map(PostDto::new).toList();
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

    public List<PostDto> getPostsByAuthor(String authorUser) {
        User author = userRepository.findByUsername(authorUser)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username " + authorUser));

        List<Post> post = postRepository.findByAuthor(author);

        return post.stream().map(postMapper::postToPostDto).collect(Collectors.toList());
    }

    public List<PostDto> getPostsByKeyword(String keyword) {
        return postRepository.findByTitleContaining(keyword).stream().map(PostDto::new).toList();

    }


    public List<PostDto> findAll(FilterParamsDto filterParamsDto) {
        var filterFunctions = PostSpecification.getFilterFunctions();
        var filterMap = filterParamsDto.toMap();
        Specification<Post> specifications = filterMap.entrySet().stream()
                .map(entry -> {
                    String filterType = entry.getKey();
                    Object filterValue = entry.getValue();
                    Function<Object, Specification<Post>> filterFunction = filterFunctions.get(filterType);
                    return filterFunction != null ? filterFunction.apply(filterValue) : null;
                })
                .filter(Objects::nonNull)
                .reduce(Specification.where(null), Specification::and);

        return postRepository.findAll(specifications)
                .stream()
                .map(PostDto::new)
                .toList();

    }

    public List<PostDto> findNearbyPosts(NearbyRequestDto req) {
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        Point point = geometryFactory.createPoint(new Coordinate(req.x(), req.y()));
        return postRepository.findNearbyPosts(point, req.distance())
                .stream()
                .map(PostDto::new)
                .toList();
    }

    public PostLocationDto getStateByPostId(Long id) {
        Post post = getPostById(id);
        State state = locationService.getStateOfPost(post);
        return PostLocationDto.builder()
                .title(post.getTitle())
                .authorName(post.getAuthor().getName())
                .location(post.getLocation())
                .state(state.getName())
                .build();
    }

    public String testCache(Long postId){
        Integer cachedLikes = postCache.getLikesFromCache(postId);
        if (cachedLikes==null) {
            postCache.updateLikesInCache(postId, 1);
            return "null";
        }
        return cachedLikes +"";
    }

    @Transactional
    public CompletableFuture<PostDto> likePost(Long postId) {
        RLock lock = postCache.getLock(postId);

        return CompletableFuture.supplyAsync(() -> {
            boolean isLocked = false;
            try {
                // Try to acquire the lock with a wait time and lease time
                isLocked = lock.tryLock(10, 5, TimeUnit.SECONDS);
                if (isLocked) {
                    Integer cachedLikes = postCache.getLikesFromCache(postId);

                    if (cachedLikes == null) {
                        Post post = postRepository.findById(postId)
                                .orElseThrow(() -> new RuntimeException("Post not found"));
                        cachedLikes = post.getLikes();
                    }

                    // Increment the likes count
                    int updatedLikes = cachedLikes + 1;

                    // Update cache asynchronously
                    CompletableFuture<Void> cacheFuture = CompletableFuture.runAsync(() -> {
                        postCache.updateLikesInCache(postId, updatedLikes);
                    }, taskExecutor);

                    // Update database asynchronously
                    CompletableFuture<Post> dbFuture = CompletableFuture.supplyAsync(() -> {
                        Post post = postRepository.findById(postId)
                                .orElseThrow(() -> new RuntimeException("Post not found"));
                        post.setLikes(updatedLikes);
                        return postRepository.save(post);
                    }, taskExecutor);

                    // Wait for both to complete and handle exceptions
                    return CompletableFuture.allOf(cacheFuture, dbFuture)
                            .thenApply(v -> postMapper.postToPostDto(dbFuture.join()))  // Return the updated post from dbFuture
                            .exceptionally(ex -> {
                                throw new RuntimeException("Error during like operation", ex);
                            })
                            .join();
                } else {
                    throw new RuntimeException("Could not acquire lock for post: " + postId);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Thread was interrupted while waiting for lock", e);
            } finally {
                if (isLocked) {
                    lock.unlock(); // Ensure the lock is released
                }
            }
        }, taskExecutor);
    }
}
