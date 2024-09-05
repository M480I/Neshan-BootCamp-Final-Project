package com.etaxi.domain.passenger.dto;

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

    public PassengerCreateResponse PassengerToPassengerResponse(Passenger passenger) {
        return PassengerCreateResponse.builder()
                .username(passenger.getUser().getUsername())
                .name(passenger.getName())
                .contactInfo(passenger.getContactInfo())
                .gender(passenger.getGender())
                .build();
    }

}
