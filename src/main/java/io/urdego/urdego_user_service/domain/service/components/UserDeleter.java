package io.urdego.urdego_user_service.domain.service.components;

import io.urdego.urdego_user_service.domain.entity.User;
import io.urdego.urdego_user_service.domain.repository.UserCharacterRepository;
import io.urdego.urdego_user_service.domain.repository.UserRepository;
import io.urdego.urdego_user_service.domain.service.UserCacheManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDeleter {
    private final UserRepository userRepository;
    private final UserCharacterRepository userCharacterRepository;

    private final UserReader userReader;
    private final UserCacheManager userCacheManager;

    @Transactional
    public void delete(Long userId, String drawalRequest) {
        User user = userReader.readByUserId(userId);
        user.setIsDeleted(drawalRequest);
        userCharacterRepository.deleteByUser(user);
        userCacheManager.deleteCache(userId);
        userRepository.save(user);
    }
}
