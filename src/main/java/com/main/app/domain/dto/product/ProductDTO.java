package com.main.app.domain.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {

    private Long id;

    private String name;

    private Long productCategoryId;

    private String description;

    private Long brandId;

    private String brandPrimaryImageUrl;

    private String primaryImageUrl;

    private Double price;

    private boolean active;

    private boolean newAdded;

    private boolean onSale;

    private HashMap<Long, List<Long>> attributeValueIds;

    private Instant dateCreated;

    private Long discount;

    private Long productPosition;

    private Long discountProductPosition;


}
