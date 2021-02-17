package com.main.app.converter.order;

import com.main.app.domain.dto.order.OrderDto;
import com.main.app.domain.model.order.CustomerOrder;

import static com.main.app.converter.order.OrderItemConverter.toDtoListItems;

public class OrderConverter {

    public static CustomerOrder toEntity(OrderDto orderDto) {
        return CustomerOrder.builder()
                .buyerCity(orderDto.getCity())
                .buyerEmail(orderDto.getEmail())
                .buyerName(orderDto.getName())
                .buyerSurname(orderDto.getSurname())
                .buyerPhone(orderDto.getPhoneNumber())
                .buyerAddress(orderDto.getAddress())
                .cityPostalCode(orderDto.getPostalCode())
                .note(orderDto.getNote())
                .deliveryType(orderDto.getDeliveryMethod() == 1 ? "STORE" : "HOME ADDRESS")
                .paymentProcessType(orderDto.getPaymentMethod() == 1 ? "ON DELIVERY" : "ONLINE")
                .build();
    }

    public static OrderDto toDto(CustomerOrder order) {
        return OrderDto.builder()
                .city(order.getBuyerCity())
                .email(order.getBuyerEmail())
                .name(order.getBuyerName())
                .surname(order.getBuyerSurname())
                .phoneNumber(order.getBuyerPhone())
                .address(order.getBuyerAddress())
                .postalCode(order.getCityPostalCode())
                .note(order.getNote())
                .deliveryMethod(order.getDeliveryType() == "STORE" ? 1 : 2)
                .paymentMethod(order.getPaymentProcessType() == "ON DELIVERY" ? 1 : 2)
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus())
                .items(toDtoListItems(order.getOrderItems()))
                .user_id(order.getUser().getId())
                .build();
    }
}
