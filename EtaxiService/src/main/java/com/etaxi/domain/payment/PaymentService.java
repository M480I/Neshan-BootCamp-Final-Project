package com.etaxi.domain.payment;

import com.etaxi.core.dto.ApiResponse;
import com.etaxi.core.exception.MultiplePaymentException;
import com.etaxi.core.exception.PaymentAuthenticationException;
import com.etaxi.core.rabbitmq.logger.LogDto;
import com.etaxi.core.rabbitmq.logger.LoggerProducer;
import com.etaxi.core.rabbitmq.sms.SmsProducer;
import com.etaxi.domain.order.Order;
import com.etaxi.domain.order.OrderService;
import com.etaxi.domain.passenger.Passenger;
import com.etaxi.domain.passenger.PassengerService;
import com.etaxi.domain.payment.dto.PaymentRequest;
import com.etaxi.domain.payment.dto.PaymentResponse;
import com.etaxi.domain.payment.dto.PaymentSmsDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentService {

    RedissonClient redisson;
    OrderService orderService;
    PassengerService passengerService;
    SmsProducer smsProducer;
    LoggerProducer logger;

    public ApiResponse<PaymentResponse> processPayment(
            PaymentRequest paymentRequest,
            Authentication authentication) {

        String lockKey =
                "payment_lock:"
                        + authentication.getName()
                        + ":"
                        + paymentRequest.getOrderId();
        RLock lock = redisson.getLock(lockKey);

        Boolean locked;
        try {
            locked = lock.tryLock(30, -1, TimeUnit.SECONDS);
            if (locked) {
                Passenger passenger = passengerService.loadByUserUsername(authentication.getName());
                Order order = orderService.loadOrderById(paymentRequest.getOrderId());
                if (!Objects.equals(order.getPassenger().getId(), passenger.getId())) {
                    throw new PaymentAuthenticationException("order doesn't belong to current user");
                }
                if (order.getIsPayed()) {
                    return new ApiResponse<PaymentResponse>(
                            "this order is already payed",
                            null
                    );
                }
                orderService.updateIsPayed(order, true);

                smsProducer.payment(
                        new PaymentSmsDto(
                                order,
                                passenger
                        )
                );
                logger.storeLog(
                        LogDto.builder()
                                .date(LocalDateTime.now())
                                .message(String.format("Payment with OrderId=%d make", order.getId()))
                                .build()
                );

                return new ApiResponse<PaymentResponse>(
                        "payment completed successfully",
                        new PaymentResponse(order, passenger)
                );
            }
            else {
                throw new MultiplePaymentException("a payment process with this id is already running");
            }
        }
        catch (InterruptedException exception) {
            return new ApiResponse<PaymentResponse>(
                    exception.getMessage(),
                    null
            );
        }
        finally {
            lock.unlock();
        }
    }
}
