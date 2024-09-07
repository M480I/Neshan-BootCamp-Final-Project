package com.etaxi.domain.order;

import com.etaxi.core.dto.ApiResponse;
import com.etaxi.core.exception.EntityNotFoundException;
import com.etaxi.core.exception.HasActiveOrderException;
import com.etaxi.core.location.LocationMapper;
import com.etaxi.core.location.LocationUtils;
import com.etaxi.domain.driver.Driver;
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
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService {

    OrderRepository orderRepository;
    PassengerService passengerService;
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

    public Order loadOrderById(int id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent())
            return order.get();
        throw new EntityNotFoundException("Order with this id doesn't exist");
    }

    public void updateIsPayed(Order order, boolean isPayed) {
        order.setIsPayed(isPayed);
        orderRepository.save(order);
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

    public ApiResponse<OrderResponse> createOrder(
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
            return new ApiResponse<OrderResponse>(
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

        driverService.updateIsAvailable(driver, false);
        orderRepository.save(order);

        return new ApiResponse<OrderResponse>(
                "driver found, order created",
                new OrderResponse(
                        order,
                        driver,
                        transportation.getTitle())
        );

    }

}
