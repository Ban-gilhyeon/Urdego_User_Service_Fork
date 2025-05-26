package io.urdego.urdego_user_service.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoCache implements Serializable {
    private Long userId;
    private String nickname;
    GameCharacter activeCharacter;
    List<UserCharacter> ownedCharacters;
    int level;
    Long exp;

    public static UserInfoCache createUserInfo(User user){
        return UserInfoCache.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .ownedCharacters(user.getOwnedCharacters())
                .activeCharacter(user.getActiveCharacter())
                .level(user.getLevel())
                .exp(user.getExp())
                .build();
    }
}
