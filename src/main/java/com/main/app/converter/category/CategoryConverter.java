package com.main.app.converter.category;

import com.main.app.domain.dto.category.CategoryDTO;
import com.main.app.domain.model.category.Category;
import com.main.app.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryConverter {

    private static CategoryService categoryService;

    @Autowired
    private CategoryConverter(CategoryService categoryService) {
        CategoryConverter.categoryService = categoryService;
    }

    public static Category DTOtoEntity(CategoryDTO categoryDTO){
        return Category
                .builder()
                .name(categoryDTO.getName())
                .parentCategory(categoryDTO.getParentProductCategoryId() != null ? categoryService.getOne(categoryDTO.getParentProductCategoryId()) : null)
                .build();
    }

    public static CategoryDTO entityToDTO(Category category){
        return CategoryDTO
                .builder()
                .id(category.getId())
                .name(category.getName())
                .parentProductCategoryId(category.getParentCategory() != null ? category.getParentCategory().getId() : null)
                .parentProductCategoryName(category.getParentCategory() != null ? category.getParentCategory().getName() : null)
                .primaryImageUrl(category.getPrimaryImageUrl())
                .dateCreated(category.getDateCreated())
                .build();
    }

    public static List<CategoryDTO> listToDTOList(List<Category> categories) {
        return categories
                .stream()
                .map(category -> entityToDTO(category))
                .collect(Collectors.toList());
    }


}
