package io.urdego.urdego_user_service.domain.service.components;

import io.urdego.urdego_user_service.domain.entity.UserInfoCache;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Slf4j
public class UserCachManager {
    private final RedisTemplate<String, Object> redisTemplate;
    private static final Duration TTL = Duration.ofMinutes(30);

    private String generateKey(Long userId){
        return "urdego_user:" + userId;
    }
    public void cachingUserInfo(Long userId, UserInfoCache userInfo){
        String key = generateKey(userId);
        redisTemplate.opsForValue().set(key, userInfo);
    }

    public Optional<UserInfoCache> getUserInfo(Long userId){
        String key = generateKey(userId);
        Object cached = redisTemplate.opsForValue().get(key);
        if(cached instanceof UserInfoCache userInfo){
            return Optional.of(userInfo);
        }
        return Optional.empty();
    }
}
