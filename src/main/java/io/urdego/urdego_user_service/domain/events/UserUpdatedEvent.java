package io.urdego.urdego_user_service.domain.events;

import io.urdego.urdego_user_service.domain.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserUpdatedEvent {
    private Long userId;

    public UserUpdatedEvent(Long userId){
        this.userId = userId;
    }
}
