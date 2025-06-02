package io.urdego.urdego_user_service.common.events;

import lombok.Getter;

@Getter
public class UserDeletedEvent {
    private Long userId;
}
