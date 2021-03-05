package com.main.app.converter.product;

import com.main.app.domain.dto.product.ProductDTO;
import com.main.app.domain.model.product.Product;
import com.main.app.service.brand.BrandService;
import com.main.app.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductConverter {

    private static BrandService brandService;

    private static CategoryService categoryService;

    @Autowired
    private ProductConverter(
            BrandService brandService,
            CategoryService productCategoryService) {
        ProductConverter.brandService = brandService;
        ProductConverter.categoryService = productCategoryService;
    }

    public static Product DTOtoEntity(ProductDTO productDTO){
        return Product
                .builder()
                .name(productDTO.getName())
                .slug(productDTO.getSlug())
                .sku(productDTO.getSku())
                .description(productDTO.getDescription())
                .productCategory(productDTO.getProductCategoryId() != null ? categoryService.getOne(productDTO.getProductCategoryId()) : null)
                .brand(productDTO.getBrandId() != null ? brandService.getOne(productDTO.getBrandId()) : null)
                .primaryImageUrl(productDTO.getPrimaryImageUrl())
                .price(productDTO.getPrice())
                .active(productDTO.isActive())
                .onSale(productDTO.isOnSale())
                .newAdded(productDTO.isNewAdded())
                .productPosition(productDTO.getProductPosition())
                .discount(productDTO.getDiscount())
                .discountProductPosition(productDTO.getDiscountProductPosition())
                .build();
    }

    public static ProductDTO entityToDTO(Product product){
        return ProductDTO
                .builder()
                .id(product.getId())
                .slug(product.getSlug())
                .sku(product.getSku())
                .name(product.getName())
                .description(product.getDescription())
                .productCategoryId(product.getProductCategory() != null ? product.getProductCategory().getId() : null)
                .brandId(product.getBrand() != null ? product.getBrand().getId() : null)
                .brandPrimaryImageUrl(product.getBrand() != null ? product.getBrand().getPrimaryImageUrl() : null)
                .primaryImageUrl(product.getPrimaryImageUrl())
                .price(product.getPrice())
                .active(product.isActive())
                .onSale(product.isOnSale())
                .newAdded(product.isNewAdded())
                .dateCreated(product.getDateCreated())
                .productPosition(product.getProductPosition())
                .discount(product.getDiscount())
                .discountProductPosition(product.getDiscountProductPosition())
                .brandName(product.getBrand().getName())
                .categoryName(product.getProductCategory().getName())
                .build();
    }

    public static ProductDTO entityToSafeDTO(Product product){
        return ProductDTO
                .builder()
                .name(product.getName())
                .description(product.getDescription())
                .productCategoryId(product.getProductCategory() != null ? product.getProductCategory().getId() : null)
                .brandId(product.getBrand() != null ? product.getBrand().getId() : null)
                .brandPrimaryImageUrl(product.getBrand() != null ? product.getBrand().getPrimaryImageUrl() : null)
                .primaryImageUrl(product.getPrimaryImageUrl())
                .price(product.getPrice())
                .active(product.isActive())
                .onSale(product.isOnSale())
                .newAdded(product.isNewAdded())
                .dateCreated(product.getDateCreated())
                .discount(product.getDiscount())
                .discountProductPosition(product.getDiscountProductPosition())
                .build();
    }

    public static List<ProductDTO> listToDTOList(List<Product> products) {
        return products
                .stream()
                .map(product -> entityToDTO(product))
                .collect(Collectors.toList());
    }

}
