package io.urdego.urdego_user_service.domain.service;

import io.urdego.urdego_user_service.domain.entity.dto.CachedUserInfo;

import java.util.List;
import java.util.Optional;

public interface UserCacheManager {
    String generateKey(Long userId);

    void cacheUserInfo(Long userId);

    Optional<CachedUserInfo> getUserInfo(Long userId);

    List<CachedUserInfo> getUserInfoToList(List<Long> userIds);

    void deleteCache(Long userId);
}
