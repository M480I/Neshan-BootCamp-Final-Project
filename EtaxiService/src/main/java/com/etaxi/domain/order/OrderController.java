package com.etaxi.domain.order;

import com.etaxi.core.dto.apiResponse;
import com.etaxi.domain.order.dto.OrderCreateRequest;
import com.etaxi.domain.order.dto.OrderResponse;
import com.etaxi.domain.passenger.PassengerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {

    OrderService orderService;
    PassengerService passengerService;

    @GetMapping("/passenger/list-orders")
    public ResponseEntity<List<OrderResponse>> getOrdersList(
            @RequestParam(required = false) Boolean isPayed,
            @RequestParam(required = false) Boolean isDone,
            Authentication authentication
    ) {
        return ResponseEntity.ok(orderService.loadOrdersByUsername(
                authentication.getName(),
                isPayed,
                isDone)
        );
    }

    @PostMapping("/passenger/request-order")
    public ResponseEntity<apiResponse<OrderResponse>> requestOrder(
            @RequestBody OrderCreateRequest orderRequest,
            Authentication authentication
            ) {
        return ResponseEntity.ok(orderService.createOrder(orderRequest, authentication));
    }

}
