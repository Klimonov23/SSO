package com.sdi.klimonov.service;

import com.sdi.klimonov.dao.entity.UserEntity;
import com.sdi.klimonov.dao.repository.RoleRepository;
import com.sdi.klimonov.dao.repository.UserRepository;
import com.sdi.klimonov.dto.AuthorizedUser;
import com.sdi.klimonov.type.AuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sdi.klimonov.mapper.AuthorizedUserMapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

    @Value("${yandex-avatar-url}")
    private String yandexAvatarUrl;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    /**
     * Создание или обновление пользователя
     */
    @Override
    @Transactional
    public UserEntity save(OAuth2User userDto, AuthProvider provider) {
        System.out.println("test");
        return switch (provider) {
            case GITHUB -> this.saveUserFromGithab(userDto);
            case YANDEX -> this.saveUserFromYandex(userDto);
        };
    }


    @Override
    public AuthorizedUser saveAndMap(OAuth2User userDto, AuthProvider provider) {
        UserEntity entity = this.save(userDto, provider);
        return AuthorizedUserMapper.map(entity, provider);
    }



    private UserEntity saveUserFromGithab(OAuth2User userDto) {
        String email = userDto.getAttribute("email");
        UserEntity user = this.getEntityByEmail(email);

        if (userDto.getAttribute("name") != null) {
            String[] splitted = ((String) userDto.getAttribute("name")).split(" ");
            user.setFirstName(splitted[0]);
            if (splitted.length > 1) {
                user.setLastName(splitted[1]);
            }
            if (splitted.length > 2) {
                user.setMiddleName(splitted[2]);
            }
        } else {
            user.setFirstName(userDto.getAttribute("login"));
            user.setLastName(userDto.getAttribute("login"));
        }

        if (userDto.getAttribute("avatar_url") != null) {
            user.setAvatarUrl(userDto.getAttribute("avatar_url"));
        }
        return userRepository.save(user);
    }





    private UserEntity saveUserFromYandex(OAuth2User userDto) {
        String email = userDto.getAttribute("default_email");
        UserEntity user = this.getEntityByEmail(email);

        if (userDto.getAttribute("first_name") != null) {
            user.setFirstName(userDto.getAttribute("first_name"));
        }

        if (userDto.getAttribute("last_name") != null) {
            user.setLastName(userDto.getAttribute("last_name"));
        }

        if (userDto.getAttribute("default_avatar_id") != null) {
            user.setAvatarUrl(this.createYandexAvatarUrl(userDto.getAttribute("default_avatar_id")));
        }
        if (userDto.getAttribute("birthday") != null) {
            user.setBirthday(LocalDate.parse(userDto.getAttribute("birthday"), DateTimeFormatter.ISO_LOCAL_DATE));
        }

        return userRepository.save(user);
    }

    private String createYandexAvatarUrl(String avatarId) {
        return yandexAvatarUrl.replace("{avatarId}", avatarId);
    }


    private UserEntity getEntityByEmail(String email) {
        if (email == null) {
           // throw new AuthException(AuthErrorCode.EMAIL_IS_EMPTY);
            email="kklimonov@yandex.ru";
        }
        UserEntity user = this.userRepository.findByEmail(email);
        if (user == null) {
            user = new UserEntity();
            user.setEmail(email);
            user.setActive(true);
            user.setRoles(List.of(roleRepository.getDefaultRole()));
        }
        return user;
    }
}
