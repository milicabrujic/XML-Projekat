package com.main.app.converter.order;

import com.main.app.domain.dto.order_item.OrderItemDto;
import com.main.app.domain.model.order_item.OrderItem;

import java.util.List;
import java.util.stream.Collectors;

public class OrderItemConverter {

    public static OrderItemDto toDtoItems(OrderItem item) {
        return OrderItemDto.builder()
                .quantity(item.getQuantity())
                .variation_id(item.getVariation().getId())
                .order_id(item.getCustomerOrder().getId())
                .name(item.getVariation().getName())
                .price(item.getVariation().getPrice().toString())
                .imageUrl(item.getVariation().getPrimaryImageUrl())
                .build();
    }

    public static List<OrderItemDto> toDtoListItems(List<OrderItem> orderItems) {
        return orderItems
                .stream()
                .map(OrderItemConverter::toDtoItems)
                .collect(Collectors.toList());
    }


}
