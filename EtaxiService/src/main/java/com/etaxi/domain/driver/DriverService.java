package com.etaxi.domain.driver;

import com.etaxi.core.security.user.Role;
import com.etaxi.core.security.user.User;
import com.etaxi.core.security.user.UserService;
import com.etaxi.domain.driver.dto.DriverCreateRequest;
import com.etaxi.domain.driver.dto.DriverCreateResponse;
import com.etaxi.domain.driver.dto.DriverMapper;
import com.etaxi.domain.transportation.Transportation;
import com.etaxi.domain.transportation.TransportationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DriverService {

    DriverRepository driverRepository;

    UserService userservice;
    DriverMapper driverMapper;
    TransportationService transportationService;

    public DriverCreateResponse createDriver(
            DriverCreateRequest driverRequest,
            Authentication authentication) {
        Driver driver = driverMapper.DriverRequestToDriver(driverRequest);
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
        return driverMapper.DriverToDriverResponse(driver);
    }

}
