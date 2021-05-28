package com.main.app.converter.product_attribute_value_converter;

import com.main.app.domain.dto.product.ProductAttributeAttrValueDTO;
import com.main.app.domain.dto.product.ProductAttributeValueDTO;
import com.main.app.domain.dto.product_attribute_category.ProductAttributeCategoryDTO;
import com.main.app.domain.model.product_attribute_category.ProductAttributeCategory;
import com.main.app.domain.model.product_attribute_values.ProductAttributeValues;

import java.util.List;
import java.util.stream.Collectors;

public class ProductAttributeValuesConverter {

    private static ProductAttributeValueDTO entityToDTO(ProductAttributeValues productValue) {
        return ProductAttributeValueDTO
                .builder()
                .id(productValue.getId())
                .productId(productValue.getProduct().getId())
                .attributeId(productValue.getAttributeValue().getAttribute().getId())
                .attributeName(productValue.getAttributeValue().getAttribute().getName())
                .attributeValueId(productValue.getAttributeValue().getId())
                .attributeValueName(productValue.getAttributeValue().getName())
                .build();
    }

    private static ProductAttributeValueDTO entityCategoryAttrToDTO(ProductAttributeCategory productAttributeCategory) {
        return ProductAttributeValueDTO
                .builder()
                .id(productAttributeCategory.getId())
                .productId(productAttributeCategory.getId())
                .attributeId(productAttributeCategory.getAttributeValue().getAttribute().getId())
                .attributeValueName(productAttributeCategory.getAttributeValue().getName())
                .attributeValueId(productAttributeCategory.getAttributeValue().getId())
                .attributeName(productAttributeCategory.getAttributeValue().getAttribute().getName())
                .build();
    }


    public static List<ProductAttributeValueDTO> listToDTOList(List<ProductAttributeValues> productAttributeValuesList) {
        return productAttributeValuesList
                .stream()
                .map(productValue -> entityToDTO(productValue))
                .collect(Collectors.toList());
    }

    public static List<ProductAttributeValueDTO> listCategoriesToDTOList(List<ProductAttributeCategory> productAttributeCategories) {
        return productAttributeCategories
                .stream()
                .map(productAttributeCategory -> entityCategoryAttrToDTO(productAttributeCategory))
                .collect(Collectors.toList());
    }


}
