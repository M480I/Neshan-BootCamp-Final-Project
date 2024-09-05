package com.etaxi.domain.passenger.dto;

import com.etaxi.core.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PassengerCreateRequest {

    @NotBlank(message = "name can not be empty")
    String name;

    @NotNull(message = "gender can not be empty")
    Gender gender;

    @NotBlank(message = "contactInfo can not be empty")
    String contactInfo;

}