package com.main.app.converter.shopping_cart_item;

import com.main.app.domain.dto.shopping_cart_item.ShoppingCartItemDto;
import com.main.app.domain.model.shopping_cart_item.ShoppingCartItem;

import java.util.List;
import java.util.stream.Collectors;

import static com.main.app.converter.variation.VariationConverter.entityToDTO;

public class ShoppingCartItemConverter {

    public static ShoppingCartItemDto toDto(ShoppingCartItem shoppingCartItem) {
        return ShoppingCartItemDto.builder()
                .id(shoppingCartItem.getId())
                .quantity(shoppingCartItem.getQuantity())
                .variationDTO(entityToDTO(shoppingCartItem.getVariation()))
                .productName(shoppingCartItem.getVariation().getProduct().getName())
                .build();
    }

    public static List<ShoppingCartItemDto> toDtoList(List<ShoppingCartItem> shoppingCartItems) {
        return shoppingCartItems
                .stream()
                .map(ShoppingCartItemConverter::toDto)
                .collect(Collectors.toList());
    }


}
