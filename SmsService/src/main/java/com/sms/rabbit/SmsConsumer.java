package com.sms.rabbit;

import com.sms.rabbit.dto.OrderSmsDto;
import com.sms.rabbit.dto.PaymentSmsDto;
import com.sms.user.User;
import com.sms.user.UserService;
import com.sms.user.UserSmsMode;
import com.sms.user.dto.UserMapper;
import com.sms.user.dto.UserSmsDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SmsConsumer {

    UserService userService;
    UserMapper userMapper;

    @RabbitListener(queues = {"${config.rabbitmq.create-order-queue}"}, priority = "2")
    public void notifyOrder(OrderSmsDto orderDto) {
        User passenger = userService.loadByModeIdAndMode(orderDto.passengerId(), UserSmsMode.PASSENGER);
        User driver = userService.loadByModeIdAndMode(orderDto.driverId(), UserSmsMode.DRIVER);

        System.out.println(
                String.format(
                        "Message Sent to %s: '%s a new order is created for you. cost: %f, approximateDuration: %f'",
                        passenger.getContactInfo(),
                        passenger.getName(),
                        orderDto.cost(),
                        orderDto.approximateDuration()
                ));
        System.out.println(
                String.format(
                        "Message Sent to %s: '%s a new order is created for you. cost: %f, approximateDuration: %f'",
                        driver.getContactInfo(),
                        driver.getName(),
                        orderDto.cost(),
                        orderDto.approximateDuration()
                ));
    }

    @RabbitListener(queues = {"${config.rabbitmq.payment-queue}"}, priority = "3")
    public void payment(PaymentSmsDto paymentDto) {
        User passenger = userService.loadByModeIdAndMode(paymentDto.getPassenger_id(), UserSmsMode.PASSENGER);

        System.out.println(
                String.format(
                        "Message Sent to %s: '%s your payment of cost %f for order %d was successful'",
                        passenger.getContactInfo(),
                        passenger.getName(),
                        paymentDto.getCost(),
                        paymentDto.getOrder_id()
                ));
    }

    @RabbitListener(queues = {"${config.rabbitmq.create-passenger-driver-queue}"}, priority = "1")
    public void createUser(UserSmsDto userSmsDto) {
        userService.createUser(userMapper.userSmsDtoToUser(userSmsDto));
    }

}
