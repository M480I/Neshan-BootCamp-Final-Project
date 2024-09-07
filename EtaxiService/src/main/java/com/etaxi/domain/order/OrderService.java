package com.etaxi.domain.order;

import com.etaxi.core.dto.apiResponse;
import com.etaxi.core.exception.HasActiveOrderException;
import com.etaxi.core.location.LocationMapper;
import com.etaxi.core.location.LocationUtils;
import com.etaxi.domain.driver.Driver;
import com.etaxi.domain.driver.DriverRepository;
import com.etaxi.domain.driver.DriverService;
import com.etaxi.domain.order.dto.OrderCreateRequest;
import com.etaxi.domain.order.dto.OrderMapper;
import com.etaxi.domain.order.dto.OrderProjection;
import com.etaxi.domain.order.dto.OrderResponse;
import com.etaxi.domain.passenger.Passenger;
import com.etaxi.domain.passenger.PassengerService;
import com.etaxi.domain.transportation.Transportation;
import com.etaxi.domain.transportation.TransportationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log
public class OrderService {

    OrderRepository orderRepository;
    PassengerService passengerService;
    DriverRepository driverRepository;
    DriverService driverService;
    OrderMapper orderMapper;
    LocationMapper locationMapper;
    TransportationService transportationService;
    LocationUtils locationUtils;

    private Double calculateCost(
            Double distance,
            Double costPerKiloMeter) {
        return (distance / 1000) * costPerKiloMeter;
    }

    private Double calculateDuration(
            Double distance,
            Double speed) {
        return ((distance / 1000) / speed) * 60;
    }

    public List<OrderResponse> loadOrdersByPassenger(
            Passenger passenger,
            Boolean isPayed,
            Boolean isDone) {
        List<OrderProjection> projections =
                orderRepository.findByPassengerId(passenger.getId(), isPayed, isDone);
        return projections.stream()
                .map(orderMapper::orderProjectonToOrderResponse)
                .collect(Collectors.toList());
    }

    public List<OrderResponse> loadOrdersByUsername(
            String username,
            Boolean isPayed,
            Boolean isDone) {
        return loadOrdersByPassenger(
                passengerService.loadByUserUsername(username),
                isPayed,
                isDone
        );
    }

    public apiResponse<OrderResponse> createOrder(
            OrderCreateRequest orderRequest,
            Authentication authentication) {

        Passenger passenger =
                passengerService.loadByUserUsername(authentication.getName());

        if (!loadOrdersByPassenger(passenger, null, false).isEmpty()) {
            throw new HasActiveOrderException("Passenger already has active order");
        }

        Transportation transportation =
                transportationService.loadByTitle(orderRequest.getTransportationTitle());

        Optional<Driver> optionalDriver = driverService.findNearestDriver(
                locationMapper.locationPairToPoint(orderRequest.getSource()),
                transportation
        );

        if (optionalDriver.isEmpty()) {
            return new apiResponse<OrderResponse>(
              "couldn't find driver is the area",
              null
            );
        }

        Driver driver = optionalDriver.get();

        Order order = orderMapper.orderRequestToOrder(orderRequest);
        Double distance = locationUtils.calculateDistance(
                order.getSource(),
                order.getDestination()
        );

        log.info(distance.toString());

        order.setTransportation(transportation);
        order.setPassenger(passenger);
        order.setDriver(driver);
        order.setCost(calculateCost(
                distance,
                transportation.getPricePerKiloMeter()
        ));
        order.setApproximateDuration(calculateDuration(
                distance,
                transportation.getSpeed()
        ));
        driver.setIsAvailable(false);
        driverRepository.save(driver);
        orderRepository.save(order);

        return new apiResponse<OrderResponse>(
                "driver found, order created",
                orderMapper.orderDriverTransportationToOrderResponse(
                        order,
                        driver,
                        transportation.getTitle())
        );

    }

}
