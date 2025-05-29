package io.urdego.urdego_user_service.domain.entity.dto;

import io.urdego.urdego_user_service.domain.entity.GameCharacter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CachedGameCharacterInfo {
    private Long characterId;
    private String name;

    public static CachedGameCharacterInfo createCachedUserCharacterInfo(GameCharacter gameCharacter){
        return CachedGameCharacterInfo.builder()
                .characterId(gameCharacter.getId())
                .name(gameCharacter.getName())
                .build();
    }
}
