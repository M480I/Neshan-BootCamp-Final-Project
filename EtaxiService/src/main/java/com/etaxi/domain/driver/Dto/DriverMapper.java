package com.etaxi.domain.driver.Dto;

import com.etaxi.core.enums.Gender;
import com.etaxi.core.location.LocationMapper;
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

    public Driver DriverRequestToDriver(DriverCreateRequest driverCreateRequest) {

        return Driver.builder()
                .name(driverCreateRequest.getName())
                .gender(driverCreateRequest.getGender())
                .contactInfo(driverCreateRequest.getContactInfo())
                .location(locationMapper.locationPairToPoint(driverCreateRequest.getLocationPair()))
                .build();
    }

    public DriverCreateResponse DriverToDriverResponse(Driver driver) {
        String transportationTitle = null;
        if (driver.getTransportation() != null)
            transportationTitle = driver.getTransportation().getTitle();
        return DriverCreateResponse.builder()
                .username(driver.getUser().getUsername())
                .name(driver.getName())
                .gender(driver.getGender())
                .transportationTitle(transportationTitle)
                .contactInfo(driver.getContactInfo())
                .locationPair(locationMapper.pointToLocationPair(driver.getLocation()))
                .build();
    }

}
