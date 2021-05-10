package com.main.app.domain.dto.category_parent;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParentCategoryDTO {

    private Long id;

    private String parentCategoryName;

    private Long parentCategoryId;

    private String categoryName;

    private Long categoryId;
}
