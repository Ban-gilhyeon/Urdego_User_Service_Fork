package io.urdego.urdego_user_service.domain.service;

import io.urdego.urdego_user_service.common.events.UserDeletedEvent;
import io.urdego.urdego_user_service.common.events.UserRegisteredEvent;
import io.urdego.urdego_user_service.common.events.UserUpdatedEvent;
import io.urdego.urdego_user_service.domain.entity.User;
import io.urdego.urdego_user_service.domain.service.components.UserCacheManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEventHandler {
    private final UserCacheManager cacheManager;

    @EventListener
    public void handleUserRegistered(UserRegisteredEvent event){
        cacheManager.cacheUserInfo(event.getUserId());
    }

    @EventListener
    public void handleUserUpdated(UserUpdatedEvent event){
        cacheManager.cacheUserInfo(event.getUserId());
    }

    @EventListener
    public void handleUserDeleted(UserDeletedEvent event){
        cacheManager.deleteCache(event.getUserId());
    }
}
