package com.sdi.klimonov.mapper;

import com.sdi.klimonov.dao.entity.AuthorityEntity;
import com.sdi.klimonov.dao.entity.RoleEntity;
import com.sdi.klimonov.dao.entity.UserEntity;
import com.sdi.klimonov.dto.AuthorizedUser;
import com.sdi.klimonov.type.AuthProvider;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class AuthorizedUserMapper {

    public AuthorizedUser map(UserEntity entity, AuthProvider provider) {
        List<GrantedAuthority> authorities = getUserAuthorities(entity);
        return AuthorizedUser.builder(entity.getEmail(), entity.getPasswordHash(), authorities)
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .middleName(entity.getMiddleName())
                .birthday(entity.getBirthday())
                .avatarUrl(entity.getAvatarUrl())
                .build();
    }

    public List<GrantedAuthority> getUserAuthorities(UserEntity entity) {
        return entity.getRoles().stream()
                .filter(RoleEntity::getActive)
                .flatMap(role -> role.getAuthorities().stream())
                .filter(AuthorityEntity::getActive)
                .map(authority -> new SimpleGrantedAuthority(authority.getCode()))
                .collect(Collectors.toList());
    }
}
