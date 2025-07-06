package io.urdego.urdego_user_service.api.user.dto.response;

import io.urdego.urdego_user_service.domain.entity.User;
import io.urdego.urdego_user_service.domain.entity.dto.CachedUserInfo;

import java.util.List;
import java.util.stream.Collectors;

public record UserSimpleResponse(
        Long userId,
        String nickname,
        String activeCharacter,
        List<String> ownedCharacters,
        int level,
        Long exp
) {
    public static UserSimpleResponse from(CachedUserInfo userInfo) {
        return new UserSimpleResponse(
                userInfo.getUserId(),
                userInfo.getNickname(),
                userInfo.getActiveCharacter().getName() == null ? null : userInfo.getActiveCharacter().getName(),
                userInfo.getOwnedCharacters().stream()
                        .map( cachedUserCharacterInfo -> cachedUserCharacterInfo.getCharacterName())
                        .collect(Collectors.toList()),
                userInfo.getLevel(),
                userInfo.getExp()
        );
    }
    public static UserSimpleResponse fromUser(User user){
        return new UserSimpleResponse(
                user.getId(),
                user.getNickname(),
                user.getActiveCharacter().getName() == null ? null : user.getActiveCharacter().getName(),
                user.getOwnedCharacters().stream()
                        .map(userCharacter -> userCharacter.getCharacter().getName())
                        .collect(Collectors.toList()),
                user.getLevel(),
                user.getExp()
        );
    }
}
