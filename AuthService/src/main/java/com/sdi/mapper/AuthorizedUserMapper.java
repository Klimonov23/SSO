package com.sdi.mapper;

import com.sdi.dao.entity.UserEntity;
import com.sdi.dto.AuthorizedUser;
import com.sdi.type.AuthProvider;
import lombok.experimental.UtilityClass;

import java.util.Collections;

@UtilityClass
public class AuthorizedUserMapper {

    public AuthorizedUser map(UserEntity entity, AuthProvider provider) {
        return AuthorizedUser.builder(entity.getEmail(), entity.getPasswordHash(), Collections.emptyList())
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .secondName(entity.getSecondName())
                .middleName(entity.getMiddleName())
                .birthday(entity.getBirthday())
                .avatarUrl(entity.getAvatarUrl())
                .build();
    }
}
