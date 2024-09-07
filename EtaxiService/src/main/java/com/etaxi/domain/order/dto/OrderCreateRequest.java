package com.etaxi.domain.order.dto;

import com.etaxi.core.location.LocationPair;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderCreateRequest {

    @NotNull(message = "source can not be null")
    LocationPair source;

    @NotNull(message = "source can not be null")
    LocationPair destination;

    @NotBlank(message = "transportationTitle is required")
    String transportationTitle;

}
