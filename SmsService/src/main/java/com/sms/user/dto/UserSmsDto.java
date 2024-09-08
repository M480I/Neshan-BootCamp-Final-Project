package com.sms.user.dto;

import com.sms.user.UserSmsMode;
import lombok.Builder;

@Builder
public record UserSmsDto(
        UserSmsMode mode,
        Integer id,
        String name,
        String contactInfo
) {
}
