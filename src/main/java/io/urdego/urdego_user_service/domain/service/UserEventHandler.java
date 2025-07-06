package io.urdego.urdego_user_service.domain.service;

import io.urdego.urdego_user_service.domain.events.UserDeletedEvent;
import io.urdego.urdego_user_service.domain.events.UserRegisteredEvent;
import io.urdego.urdego_user_service.domain.events.UserUpdatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserEventHandler {
    private final UserCacheManager cacheManager;

    @EventListener
    public void handleUserRegistered(UserRegisteredEvent event){
        log.info("User Registered Event : {} ", event.getUserId());
        cacheManager.cacheUserInfo(event.getUserId());
    }

    @EventListener
    public void handleUserUpdated(UserUpdatedEvent event){
        log.info("User Updated Event : {} ", event.getUserId());
        cacheManager.cacheUserInfo(event.getUserId());
    }

    @EventListener
    public void handleUserDeleted(UserDeletedEvent event){
        log.info("User Deleted Event : {} ", event.getUserId());
        cacheManager.deleteCache(event.getUserId());
    }
}
