package io.urdego.urdego_user_service.domain.events;

import lombok.Getter;

@Getter
public class UserRegisteredEvent{
    private Long userId;

    public UserRegisteredEvent(Long userId){
        this.userId = userId;
    }
}
