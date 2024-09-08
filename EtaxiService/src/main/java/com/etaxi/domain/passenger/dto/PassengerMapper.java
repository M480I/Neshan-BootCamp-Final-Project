package com.etaxi.domain.passenger.dto;

import com.etaxi.core.user.dto.UserSmsDto;
import com.etaxi.core.user.dto.UserSmsMode;
import com.etaxi.domain.passenger.Passenger;
import org.springframework.stereotype.Component;

@Component
public class PassengerMapper {

    public Passenger passengerRequestToPassenger(
            PassengerCreateRequest passengerRequest) {
        return Passenger.builder()
                .name(passengerRequest.getName())
                .contactInfo(passengerRequest.getContactInfo())
                .gender(passengerRequest.getGender())
                .build();
    }

    public PassengerCreateResponse passengerToPassengerResponse(Passenger passenger) {
        return PassengerCreateResponse.builder()
                .username(passenger.getUser().getUsername())
                .name(passenger.getName())
                .contactInfo(passenger.getContactInfo())
                .gender(passenger.getGender())
                .build();
    }


    public UserSmsDto passengerToPassengerSmsDto(Passenger passenger) {
        return UserSmsDto.builder()
                .mode(UserSmsMode.PASSENGER)
                .id(passenger.getId())
                .name(passenger.getName())
                .contactInfo(passenger.getContactInfo())
                .build();
    }

}
