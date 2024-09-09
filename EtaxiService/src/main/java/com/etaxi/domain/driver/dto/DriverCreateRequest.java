package com.etaxi.domain.driver.dto;

import com.etaxi.core.enums.Gender;
import com.etaxi.core.location.LocationPair;
import com.etaxi.core.location.validation.LocationPairValidation;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DriverCreateRequest {

    @NotBlank(message = "name is required")
    String name;

    String transportationTitle;

    @NotBlank(message = "contactInfo is required")
    @Pattern(regexp = "^\\+98(\\d{10})$", message = "Invalid phone number")
    @JsonProperty("contact")
    String contactInfo;

    @NotNull(message = "gender can not be null")
    Gender gender;

    @NotNull(message = "locationPair can not be null")
    @LocationPairValidation
    LocationPair locationPair;

}
