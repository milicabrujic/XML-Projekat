package com.main.app.domain.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.lang.reflect.Array;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {

    private Long id;

    private String sku;

    private String slug;

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

    private String vremeIsporuke;

    private HashMap<Long, List<Long>> sunshineUseIds;

    private HashMap<String, String> attrCategoryContent;

    private HashMap<Long, List<Long>> attributeValueIds;

    private Instant dateCreated;

    private Long discount;

    private Long productPosition;

    private Long discountProductPosition;

    private String brandName;

    private String categoryName;

}
