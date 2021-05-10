package com.main.app.domain.dto.product_category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ProductCategoryDTO {

    private Long id;

    private Long productId;

    private String productName;

    private Long categoryId;

    private String categoryName;

}
