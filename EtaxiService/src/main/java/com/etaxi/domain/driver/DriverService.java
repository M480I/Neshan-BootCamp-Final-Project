package com.etaxi.domain.driver;

import com.etaxi.core.security.user.Role;
import com.etaxi.core.security.user.User;
import com.etaxi.core.security.user.UserService;
import com.etaxi.domain.driver.dto.DriverCreateRequest;
import com.etaxi.domain.driver.dto.DriverCreateResponse;
import com.etaxi.domain.driver.dto.DriverMapper;
import com.etaxi.domain.order.OrderProperties;
import com.etaxi.domain.transportation.Transportation;
import com.etaxi.domain.transportation.TransportationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.locationtech.jts.geom.Point;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DriverService {

    DriverRepository driverRepository;

    UserService userservice;
    DriverMapper driverMapper;
    TransportationService transportationService;
    OrderProperties orderProperties;

    public DriverCreateResponse createDriver(
            DriverCreateRequest driverRequest,
            Authentication authentication) {
        Driver driver = driverMapper.driverRequestToDriver(driverRequest);
        User user =
                (User) userservice.loadUserByUsername(authentication.getName());
        if (driverRequest.getTransportationTitle() != null) {
            Transportation transportation = transportationService.loadByTitle(
                    driverRequest.getTransportationTitle());
            driver.setTransportation(transportation);
        }
        driver.setUser(user);
        user.setRole(Role.DRIVER);
        driverRepository.save(driver);
        return driverMapper.driverToDriverResponse(driver);
    }

    public void updateIsAvailable(Driver driver, Boolean isAvailable) {
        driver.setIsAvailable(isAvailable);
        driverRepository.save(driver);
    }

    public Optional<Driver> findNearestDriver(
            Point location,
            Transportation transportation) {
        return driverRepository.findNearestDriver(
                location,
                transportation.getId(),
                orderProperties.getDriverSearchRadius()
        );
    }

}
