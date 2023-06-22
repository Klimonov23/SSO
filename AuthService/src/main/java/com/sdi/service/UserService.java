package com.sdi.service;

import com.sdi.dao.entity.UserEntity;
import com.sdi.dto.AuthorizedUser;
import com.sdi.type.AuthProvider;
import org.springframework.security.oauth2.core.user.OAuth2User;


public interface UserService {

    UserEntity save(OAuth2User userDto, AuthProvider provider);

    AuthorizedUser saveAndMap(OAuth2User userDto, AuthProvider provider);

}
