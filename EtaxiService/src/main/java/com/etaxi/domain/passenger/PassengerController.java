package com.etaxi.domain.passenger;

import com.etaxi.domain.order.Order;
import com.etaxi.domain.passenger.dto.PassengerCreateRequest;
import com.etaxi.domain.passenger.dto.PassengerCreateResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/passenger")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PassengerController {

    PassengerService passengerService;

    @PostMapping("/signup")
    ResponseEntity<PassengerCreateResponse> signup(
            @Valid @RequestBody PassengerCreateRequest passengerRequest,
            Authentication authentication
    ) {
        return ResponseEntity.ok(passengerService.createPassenger(passengerRequest, authentication));
    }

    @PostMapping("/list-orders")
    ResponseEntity<List<Order>>

}
