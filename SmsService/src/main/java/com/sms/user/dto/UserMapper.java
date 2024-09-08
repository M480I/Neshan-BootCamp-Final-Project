package com.sms.user.dto;

import com.sms.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User userSmsDtoToUser(UserSmsDto userDto) {
        return User.builder()
                .mode(userDto.mode())
                .name(userDto.name())
                .modeId(userDto.id())
                .contactInfo(userDto.contactInfo())
                .build();
    }

}
