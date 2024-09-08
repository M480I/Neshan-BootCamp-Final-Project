package com.etaxi.domain.order.dto;

import com.etaxi.core.location.LocationMapper;
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

    public OrderSmsDto orderToOrderSmsDto(Order order) {
        return OrderSmsDto.builder()
                .passengerId(order.getPassenger().getId())
                .driverId(order.getDriver().getId())
                .transportationTitle(order.getTransportation().getTitle())
                .cost(order.getCost())
                .approximateDuration(order.getApproximateDuration())
                .date(order.getDate())
                .build();
    }

}
