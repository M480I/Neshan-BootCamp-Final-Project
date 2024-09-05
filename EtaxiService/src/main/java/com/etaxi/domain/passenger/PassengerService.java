package com.etaxi.domain.passenger;

import com.etaxi.core.security.user.Role;
import com.etaxi.core.security.user.User;
import com.etaxi.core.security.user.UserService;
import com.etaxi.domain.passenger.Dto.PassengerCreateRequest;
import com.etaxi.domain.passenger.Dto.PassengerCreateResponse;
import com.etaxi.domain.passenger.Dto.PassengerMapper;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PassengerService {

    PassengerRepository passengerRepository;
    UserService userService;
    PassengerMapper passengerMapper;

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
        return passengerMapper.PassengerToPassengerResponse(passenger);
    }

}
