package com.main.app.domain.dto.shopping_cart_item;

import com.main.app.domain.dto.variation.VariationDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.PositiveOrZero;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoppingCartItemDto {

    private Long id;

    private Long variationId;

    private VariationDTO variationDTO;

    @PositiveOrZero
    private Integer quantity;

    private String productName;
}
