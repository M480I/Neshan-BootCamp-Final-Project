package com.etaxi.domain.driver.dto;

import com.etaxi.core.location.LocationMapper;
import com.etaxi.core.user.dto.UserSmsDto;
import com.etaxi.core.user.dto.UserSmsMode;
import com.etaxi.domain.driver.Driver;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DriverMapper {

    LocationMapper locationMapper;

    public Driver driverRequestToDriver(DriverCreateRequest driverCreateRequest) {

        return Driver.builder()
                .name(driverCreateRequest.getName())
                .gender(driverCreateRequest.getGender())
                .contactInfo(driverCreateRequest.getContactInfo())
                .location(locationMapper.locationPairToPoint(driverCreateRequest.getLocationPair()))
                .build();
    }

    public DriverCreateResponse driverToDriverResponse(Driver driver) {
        String transportationTitle = null;
        if (driver.getTransportation() != null)
            transportationTitle = driver.getTransportation().getTitle();
        return DriverCreateResponse.builder()
                .username(driver.getUser().getUsername())
                .name(driver.getName())
                .gender(driver.getGender())
                .transportationTitle(transportationTitle)
                .contactInfo(driver.getContactInfo())
                .location(driver.getLocation())
                .isAvailable(driver.getIsAvailable())
                .build();
    }

    public UserSmsDto driverToDriverSmsDto(Driver driver) {
        return UserSmsDto.builder()
                .mode(UserSmsMode.DRIVER)
                .id(driver.getId())
                .name(driver.getName())
                .contactInfo(driver.getContactInfo())
                .build();
    }

}
