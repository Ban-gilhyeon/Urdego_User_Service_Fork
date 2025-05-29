package io.urdego.urdego_user_service.domain.service.components;

import io.urdego.urdego_user_service.domain.entity.User;
import io.urdego.urdego_user_service.domain.entity.dto.CachedUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Slf4j
public class UserCacheManager {
    private final RedisTemplate<String, Object> redisTemplate;
    private static final Duration TTL = Duration.ofMinutes(30);

    private String generateKey(Long userId){
        return "urdego_user:" + userId;
    }
    public void cacheUserInfo(Long userId, User user){
        String key = generateKey(userId);
        CachedUserInfo userInfo = CachedUserInfo.createUserInfo(user);
        redisTemplate.opsForValue().set(key, userInfo, TTL);
    }

    public Optional<CachedUserInfo> getUserInfo(Long userId){
        String key = generateKey(userId);
        Object cached = redisTemplate.opsForValue().get(key);
        if(cached instanceof CachedUserInfo userInfo){
            return Optional.of(userInfo);
        }
        return Optional.empty();
    }

    public void updateCache(){

    }
}
