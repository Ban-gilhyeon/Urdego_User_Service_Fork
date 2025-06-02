package io.urdego.urdego_user_service.common.events;

import io.urdego.urdego_user_service.domain.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserUpdatedEvent {
    private Long userId;
}
