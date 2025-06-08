package io.urdego.urdego_user_service.infra.redis;

import io.urdego.urdego_user_service.domain.entity.User;
import io.urdego.urdego_user_service.domain.entity.dto.CachedUserInfo;
import io.urdego.urdego_user_service.domain.service.UserCacheManager;
import io.urdego.urdego_user_service.domain.service.components.UserReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Slf4j
public class UserCacheManagerImpl implements UserCacheManager {
    private final RedisTemplate<String, Object> redisTemplate;
    private final UserReader userReader;
    private static final Duration TTL = Duration.ofMinutes(30);
    private static final int BATCH_SIZE = 100;
    private static final String PREFIX = "urdego_user";

    @Override
     public String generateKey(Long userId){
        return PREFIX + userId;
    }

    @Override
    public void cacheUserInfo(Long userId){
        String key = generateKey(userId);
        User user = userReader.readByUserId(userId);
        CachedUserInfo userInfo = CachedUserInfo.createUserInfo(user);

        redisTemplate.opsForValue().set(key, userInfo, TTL);
        log.info("UserCached info : {}", userId);
    }

    @Override
    public Optional<CachedUserInfo> getUserInfo(Long userId){
        String key = generateKey(userId);
        Object cached = redisTemplate.opsForValue().get(key);
        if(cached instanceof CachedUserInfo userInfo){
            log.info("Read Success userInfo {}", ( (CachedUserInfo) cached).getUserId());
            return Optional.of((CachedUserInfo) cached);
        }
        log.info("userInfo is empty");
        return Optional.empty();
    }
    //게임 서비스에서 사용
    // 한 게임 당 최대 8명 참가 가능하기 때문에 userIds의 최대 사이즈는 8 이하
    @Override
    public List<CachedUserInfo> getUserInfoToList(List<Long> userIds){
        List<CachedUserInfo> result = new ArrayList<>();
        List<String> keys = new ArrayList<>();
        for(Long id : userIds){
            String key = generateKey(id);
            keys.add(key);
        }

        List<Object> resultObj = redisTemplate.opsForValue().multiGet(keys);
        List<Long> missedIds = new ArrayList<>();
        for(int i = 0; i < userIds.size(); i++){
            Object obj = resultObj.get(i);
            if(obj instanceof CachedUserInfo userInfo){
                result.add((CachedUserInfo) obj);
            }else {
                missedIds.add(userIds.get(i));
            }
        }
        List<User> missedUser = userReader.findByIds(missedIds);

        //순서 배제
        for(User user :missedUser){
            CachedUserInfo missedInfo = CachedUserInfo.createUserInfo(user);
            result.add(missedInfo);
            //다시 캐싱
            redisTemplate.opsForValue().set(generateKey(user.getId()), missedInfo, TTL);
        }
        return result;
    }

    @Override
    public void deleteCache(Long userId){
        redisTemplate.delete(generateKey(userId));
    }

}
