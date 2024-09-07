package com.etaxi.domain.payment;

import com.etaxi.core.dto.ApiResponse;
import com.etaxi.domain.payment.dto.PaymentRequest;
import com.etaxi.domain.payment.dto.PaymentResponse;
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

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentController {

    PaymentService paymentService;


    @PostMapping("/passenger/pay")
    public ResponseEntity<ApiResponse<PaymentResponse>> payOrder(
            @Valid @RequestBody PaymentRequest paymentRequest,
            Authentication authentication
    ) {
        return ResponseEntity.ok(paymentService.processPayment(paymentRequest, authentication));
    }

}
