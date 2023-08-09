package com.sdi.klimonov.service;

import com.sdi.klimonov.dao.entity.UserEntity;
import com.sdi.klimonov.dto.AuthorizedUser;
import com.sdi.klimonov.type.AuthProvider;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface UserService {

    UserEntity save(OAuth2User userDto, AuthProvider provider);

    AuthorizedUser saveAndMap(OAuth2User userDto, AuthProvider provider);

}
