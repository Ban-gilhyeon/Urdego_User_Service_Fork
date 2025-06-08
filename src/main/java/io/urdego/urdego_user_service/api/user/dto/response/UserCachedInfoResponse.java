package io.urdego.urdego_user_service.api.user.dto.response;

import io.urdego.urdego_user_service.domain.entity.dto.CachedGameCharacterInfo;
import io.urdego.urdego_user_service.domain.entity.dto.CachedUserCharacterInfo;
import io.urdego.urdego_user_service.domain.entity.dto.CachedUserInfo;

import java.util.List;
import java.util.stream.Collectors;

public record UserCachedInfoResponse(
        Long userId,
        String nickname,
        String activeCharacter,
        List<String>ownedCharacters,
        int level,
        Long exp
) {
    public static UserCachedInfoResponse from(CachedUserInfo userInfo){
        return new UserCachedInfoResponse(
                userInfo.getUserId(),
                userInfo.getNickname(),
                userInfo.getActiveCharacter().getName(),
                userInfo.getOwnedCharacters().stream()
                        .map(characterName -> characterName.getCharacterName())
                        .collect(Collectors.toList()),
                userInfo.getLevel(),
                userInfo.getExp()
        );
    }
}
