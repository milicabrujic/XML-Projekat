package com.main.app.converter.category_parent;

import com.main.app.domain.dto.category_parent.ParentCategoryDTO;
import com.main.app.domain.model.category_parent.ParentCategory;
import com.main.app.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ParentCategoryConverter {

    private static CategoryService categoryService;

    @Autowired
    private ParentCategoryConverter(CategoryService categoryService) {
        ParentCategoryConverter.categoryService = categoryService;
    }

    public static ParentCategory DTOtoEntity(ParentCategoryDTO categoryDTO){
        return ParentCategory
                .builder()
                .category(categoryDTO.getCategoryId() != null ? categoryService.getOne(categoryDTO.getCategoryId()) : null)
                .category(categoryDTO.getParentCategoryId() != null ? categoryService.getOne(categoryDTO.getParentCategoryId()) : null)
                .build();
    }

    public static ParentCategoryDTO entityToDTO(ParentCategory category){
        return ParentCategoryDTO
                .builder()
                .id(category.getId())
                .categoryId(category.getCategory().getId())
                .categoryName(category.getCategory().getName())
                .parentCategoryId(category.getParentCategory().getId())
                .parentCategoryName(category.getParentCategory().getName())
                .build();
    }

    public static List<ParentCategoryDTO> listToDTOList(List<ParentCategory> categories) {
        return categories
                .stream()
                .map(productCategory -> entityToDTO(productCategory))
                .collect(Collectors.toList());
    }


}
