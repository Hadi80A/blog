package org.hammasir.blog.component;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class PostCache {

    private final RedissonClient redissonClient;
    private static final String LIKES_CACHE = "postLikesCache";
    private static final String LOCK_KEY_PREFIX = "postLock:";
    RMapCache<Long, Integer> likesCache;

    @PostConstruct
    public void init(){
        likesCache= redissonClient.getMapCache(LIKES_CACHE);
    }

    public Integer getLikesFromCache(Long postId) {
        return likesCache.get(postId);
    }

    public void updateLikesInCache(Long postId, int likes) {
        likesCache.put(postId, likes, 10, TimeUnit.MINUTES);
    }

    public RLock getLock(Long postId) {
        return redissonClient.getLock(LOCK_KEY_PREFIX + postId);
    }
}
