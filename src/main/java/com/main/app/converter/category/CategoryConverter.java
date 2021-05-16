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
                .title(categoryDTO.getTitle())
                .subtitle(categoryDTO.getSubtitle())
                .contentText(categoryDTO.getContentText())
                .description(categoryDTO.getDescription())
                .firstOrderCategory(categoryDTO.isFirstOrderCategory())
                .secondOrderCategory(categoryDTO.isSecondOrderCategory())
                .thirdOrderCategory(categoryDTO.isThirdOrderCategory())
                .build();
    }

    public static CategoryDTO entityToDTO(Category category){
        return CategoryDTO
                .builder()
                .id(category.getId())
                .name(category.getName())
                .title(category.getTitle())
                .subtitle(category.getSubtitle())
                .contentText(category.getContentText())
                .description(category.getDescription())
                .primaryImageUrl(category.getPrimaryImageUrl())
                .dateCreated(category.getDateCreated())
                .firstOrderCategory(category.isFirstOrderCategory())
                .secondOrderCategory(category.isSecondOrderCategory())
                .thirdOrderCategory(category.isThirdOrderCategory())
                .categoryOrder(category.isFirstOrderCategory() ? 1 : (category.isSecondOrderCategory() ? 2 : (category.isThirdOrderCategory() ? 3 : 0)))
                .build();
    }

    public static List<CategoryDTO> listToDTOList(List<Category> categories) {
        return categories
                .stream()
                .map(category -> entityToDTO(category))
                .collect(Collectors.toList());
    }

    public static CategoryDTO entityToFilterDTO(Category productCategory){
        return CategoryDTO
                .builder()
                .id(productCategory.getId())
                .name(productCategory.getName())
                .primaryImageUrl(productCategory.getPrimaryImageUrl())
                .title(productCategory.getTitle())
                .subtitle(productCategory.getSubtitle())
                .contentText(productCategory.getContentText())
                .description(productCategory.getDescription())
                .dateCreated(productCategory.getDateCreated())
                .firstOrderCategory(productCategory.isFirstOrderCategory())
                .secondOrderCategory(productCategory.isSecondOrderCategory())
                .thirdOrderCategory(productCategory.isThirdOrderCategory())
                .categoryOrder(productCategory.isFirstOrderCategory() ? 1 : (productCategory.isSecondOrderCategory() ? 2 : (productCategory.isThirdOrderCategory() ? 3 : 0)))
                .build();
    }

    public static List<CategoryDTO> listToFilterDTOList(List<Category> categories) {
        return categories
                .stream()
                .map(productCategory -> entityToFilterDTO(productCategory))
                .collect(Collectors.toList());
    }

}
