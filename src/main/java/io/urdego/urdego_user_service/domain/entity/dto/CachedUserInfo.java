package io.urdego.urdego_user_service.domain.entity.dto;

import io.urdego.urdego_user_service.domain.entity.GameCharacter;
import io.urdego.urdego_user_service.domain.entity.User;
import io.urdego.urdego_user_service.domain.entity.UserCharacter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CachedUserInfo implements Serializable {
    private Long userId;
    private String nickname;
    private CachedGameCharacterInfo activeCharacter;
    private List<CachedUserCharacterInfo> ownedCharacters;
    private int level;
    private Long exp;

    public static CachedUserInfo createUserInfo(User user){
        List<CachedUserCharacterInfo> userCharacterInfos = new ArrayList<>();
        for(UserCharacter userCharacter : user.getOwnedCharacters()){
            userCharacterInfos.add(CachedUserCharacterInfo.createdCachedUserCharacterInfo(userCharacter));
        }
        return CachedUserInfo.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .ownedCharacters(userCharacterInfos)
                .activeCharacter(CachedGameCharacterInfo
                        .createCachedUserCharacterInfo(user.getActiveCharacter()))
                .level(user.getLevel())
                .exp(user.getExp())
                .build();
    }
}
