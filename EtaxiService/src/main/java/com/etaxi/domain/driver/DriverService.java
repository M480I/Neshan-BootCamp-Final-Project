package com.etaxi.domain.driver;

import com.etaxi.core.rabbitmq.logger.LogDto;
import com.etaxi.core.rabbitmq.logger.LoggerProducer;
import com.etaxi.core.rabbitmq.sms.SmsProducer;
import com.etaxi.core.user.Role;
import com.etaxi.core.user.User;
import com.etaxi.core.user.UserService;
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

import java.time.LocalDateTime;
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
    SmsProducer smsProducer;
    LoggerProducer logger;

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

        smsProducer.createDriverOrPassenger(
                driverMapper.driverToDriverSmsDto(driver));
        logger.storeLog(
                LogDto.builder()
                        .date(LocalDateTime.now())
                        .message(String.format("New Driver with Id=%d create", driver.getId()))
                        .build()
        );

        return driverMapper.driverToDriverResponse(driver);
    }

    public void updateIsAvailable(Driver driver, Boolean isAvailable) {
        driver.setIsAvailable(isAvailable);
        driverRepository.save(driver);
        logger.storeLog(
                LogDto.builder()
                        .date(LocalDateTime.now())
                        .message(String.format("Driver with id=%d update isAvailable", driver.getId()))
                        .build()
        );
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
