package com.main.app.converter.product_cateogry;

import com.main.app.domain.dto.product_category.ProductCategoryDTO;
import com.main.app.domain.model.product_category.ProductCategory;
import com.main.app.service.category.CategoryService;
import com.main.app.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductCategoryConverter {

    private static ProductService productService;
    private static CategoryService categoryService;

    @Autowired
    private ProductCategoryConverter(ProductService productService, CategoryService categoryService ) {
        ProductCategoryConverter.productService = productService;
        ProductCategoryConverter.categoryService = categoryService;
    }

    public static ProductCategory DTOtoEntity(ProductCategoryDTO productCategoryDTO){
        return ProductCategory
                .builder()
                .category(productCategoryDTO.getCategoryId() != null ? categoryService.getOne(productCategoryDTO.getCategoryId()) : null)
                .product(productCategoryDTO.getProductId() != null ? productService.getOne(productCategoryDTO.getProductId()) : null)
                .build();
    }

    public static ProductCategoryDTO entityToDTO(ProductCategory productCategory){
        return ProductCategoryDTO
                .builder()
                .id(productCategory.getId())
                .categoryId(productCategory.getCategory() != null ? productCategory.getCategory().getId() : null)
                .categoryName(productCategory.getCategory() != null ? productCategory.getCategory().getName() : null)
                .productId(productCategory.getProduct() != null ? productCategory.getProduct().getId() : null)
                .productName(productCategory.getProduct() != null ? productCategory.getProduct().getName() : null)
                .build();
    }


    public static List<ProductCategoryDTO> listToDTOList(List<ProductCategory> productCategories) {
        return productCategories
                .stream()
                .map(productCategory -> entityToDTO(productCategory))
                .collect(Collectors.toList());
    }

}
