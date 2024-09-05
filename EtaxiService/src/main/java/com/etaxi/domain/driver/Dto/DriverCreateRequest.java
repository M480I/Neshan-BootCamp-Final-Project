package com.etaxi.domain.driver.Dto;

import com.etaxi.core.location.LocationPair;
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

    @NotBlank(message = "name can not be empty")
    String name;

    String transportationTitle;

    @NotBlank(message = "contactInfo can not be empty")
    @Pattern(regexp = "^\\+98(\\d{10})$", message = "Invalid phone number")
    String contactInfo;

    @NotBlank(message = "gender can not be empty")
    String gender;

    @NotNull(message = "locationPair can not be empty")
    LocationPair locationPair;

}
