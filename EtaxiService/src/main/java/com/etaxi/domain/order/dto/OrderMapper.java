package com.etaxi.domain.order.dto;

import com.etaxi.core.location.LocationMapper;
import com.etaxi.domain.driver.Driver;
import com.etaxi.domain.order.Order;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderMapper {

    LocationMapper locationMapper;

    public Order orderRequestToOrder(OrderCreateRequest orderRequest) {
        return Order.builder()
                .source(locationMapper.locationPairToPoint(orderRequest.getSource()))
                .destination(locationMapper.locationPairToPoint(orderRequest.getDestination()))
                .build();
    }

    public OrderResponse orderProjectonToOrderResponse(
            OrderProjection projection) {
        return OrderResponse.builder()
                .id(projection.getId())
                .driverName(projection.getDriverName())
                .driverContactInfo(projection.getDriverContactInfo())
                .transportationTitle(projection.getTransportationTitle())
                .source(projection.getSource())
                .destination(projection.getDestination())
                .date(projection.getDate())
                .cost(projection.getCost())
                .approximateDuration(projection.getApproximateDuration())
                .isPayed(projection.getIsPayed())
                .isDone(projection.getIsDone())
                .build();
    }

    public OrderResponse orderDriverTransportationToOrderResponse(
            Order order,
            Driver driver,
            String transportationTitle) {
        return OrderResponse.builder()
                .id(order.getId())
                .driverName(driver.getName())
                .driverContactInfo(driver.getContactInfo())
                .source(order.getSource())
                .destination(order.getDestination())
                .transportationTitle(transportationTitle)
                .date(order.getDate())
                .cost(order.getCost())
                .approximateDuration(order.getApproximateDuration())
                .isPayed(order.getIsPayed())
                .isDone(order.getIsDone())
                .build();
    }

}
