package io.urdego.urdego_user_service.domain.entity.dto;

import io.urdego.urdego_user_service.domain.entity.UserCharacter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CachedUserCharacterInfo {
    private Long userId;
    private String characterName;

    public static CachedUserCharacterInfo createdCachedUserCharacterInfo(UserCharacter userCharacter){
        return CachedUserCharacterInfo.builder()
                .userId(userCharacter.getUserCharacterPK().getUserId())
                .characterName(userCharacter.getCharacter().getName())
                .build();
    }
}
