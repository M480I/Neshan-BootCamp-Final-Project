package com.etaxi.domain.passenger;

import com.etaxi.core.rabbitmq.logger.LogDto;
import com.etaxi.core.rabbitmq.logger.LoggerProducer;
import com.etaxi.core.rabbitmq.sms.SmsProducer;
import com.etaxi.core.user.Role;
import com.etaxi.core.user.User;
import com.etaxi.core.user.UserService;
import com.etaxi.domain.passenger.dto.PassengerCreateRequest;
import com.etaxi.domain.passenger.dto.PassengerCreateResponse;
import com.etaxi.domain.passenger.dto.PassengerMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PassengerService {

    PassengerRepository passengerRepository;
    UserService userService;
    PassengerMapper passengerMapper;
    SmsProducer smsProducer;
    LoggerProducer logger;

    public PassengerCreateResponse createPassenger(
            PassengerCreateRequest passengerRequest,
            Authentication authentication) {
        Passenger passenger =
                passengerMapper.passengerRequestToPassenger(passengerRequest);
        User user =
                (User) userService.loadUserByUsername(authentication.getName());
        passenger.setUser(user);
        user.setRole(Role.PASSENGER);
        passengerRepository.save(passenger);

        smsProducer.createDriverOrPassenger(
                passengerMapper.passengerToPassengerSmsDto(passenger));
        logger.storeLog(
                LogDto.builder()
                        .date(LocalDateTime.now())
                        .message(String.format("New Passenger with Id=%d create", passenger.getId()))
                        .build()
        );

        return passengerMapper.passengerToPassengerResponse(passenger);
    }

    public Passenger loadByUserUsername(String username) {
        Optional<Passenger> passenger = passengerRepository.findByUserUsername(username);
        if (passenger.isPresent())
            return passenger.get();
        throw new UsernameNotFoundException("username not found");
    }

}
