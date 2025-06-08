package io.urdego.urdego_user_service.domain.events;

import lombok.Getter;

@Getter
public class UserDeletedEvent {
    private Long userId;
}
