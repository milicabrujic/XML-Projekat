package com.main.app.domain.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchDTO {

    private String searchParam;

    private List<Long> productCategoryIds;

    private boolean findByNewAdded;

//    private List<Integer> filter;
//
//    private List<Integer> additionalFilter;

}
