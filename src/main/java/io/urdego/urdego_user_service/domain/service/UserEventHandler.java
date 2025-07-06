package io.urdego.urdego_user_service.domain.service;

import io.urdego.urdego_user_service.domain.events.UserDeletedEvent;
import io.urdego.urdego_user_service.domain.events.UserRegisteredEvent;
import io.urdego.urdego_user_service.domain.events.UserUpdatedEvent;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserEventHandler {
    private final UserCacheManager cacheManager;
    private final BlockingQueue<Long> eventQue = new LinkedBlockingQueue<>();

    @PostConstruct
    public void init(){
        Thread consumerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try{
                        Long userId = eventQue.take();
                        cacheManager.cacheUserInfo(userId);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        });
        consumerThread.setDaemon(true);
        consumerThread.start();
    }

    @EventListener
    public void handleUserRegistered(UserRegisteredEvent event){
        log.info("User Registered Event : {} ", event.getUserId());
        //cacheManager.cacheUserInfo(event.getUserId());
        eventQue.offer(event.getUserId());
    }

    @EventListener
    public void handleUserUpdated(UserUpdatedEvent event){
        log.info("User Updated Event : {} ", event.getUserId());
        //cacheManager.cacheUserInfo(event.getUserId());
        eventQue.offer(event.getUserId());
    }

    @EventListener
    public void handleUserDeleted(UserDeletedEvent event){
        log.info("User Deleted Event : {} ", event.getUserId());
        cacheManager.deleteCache(event.getUserId());
    }
}
