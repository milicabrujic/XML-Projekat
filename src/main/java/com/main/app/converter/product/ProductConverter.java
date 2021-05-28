package com.main.app.converter.product;

import com.main.app.converter.product_cateogry.ProductCategoryConverter;
import com.main.app.domain.dto.product.ProductDTO;
import com.main.app.domain.model.product.Product;
import com.main.app.service.brand.BrandService;
import com.main.app.service.category.CategoryService;
import com.main.app.service.variation.VariationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static com.main.app.converter.product_cateogry.ProductCategoryConverter.*;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductConverter {

    private static BrandService brandService;

    private static VariationService variationService;

    private static CategoryService categoryService;

    @Autowired
    private ProductConverter(
            BrandService brandService,
            VariationService variationService,
            CategoryService productCategoryService) {
            ProductConverter.variationService = variationService;
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
                .brand(productDTO.getBrandId() != null ? brandService.getOne(productDTO.getBrandId()) : null)
                .primaryImageUrl(productDTO.getPrimaryImageUrl())
                .price(productDTO.getPrice())
                .active(productDTO.isActive())
                .onSale(productDTO.isOnSale())
                .newAdded(productDTO.isNewAdded())
                .productPosition(productDTO.getProductPosition())
                .discount(productDTO.getDiscount())
                .discountProductPosition(productDTO.getDiscountProductPosition())
                .vremeIsporukeDo(productDTO.getVremeIsporukeDo())
                .vremeIsporukeOd(productDTO.getVremeIsporukeOd())
                .available(productDTO.getAvailable())
                .selfTransport(productDTO.isSelfTransport())
                .suggestedProductIdSlot1(productDTO.getSuggestedProductIdSlot1())
                .suggestedProductIdSlot2(productDTO.getSuggestedProductIdSlot2())
                .suggestedProductIdSlot3(productDTO.getSuggestedProductIdSlot3())
                .suggestedProductIdSlot4(productDTO.getSuggestedProductIdSlot4())
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
                .brandName(product.getBrand() != null ? product.getBrand().getName()  : null )
                .vremeIsporukeOd(product.getVremeIsporukeOd())
                .vremeIsporukeDo(product.getVremeIsporukeDo())
                .available(product.getAvailable())
                .selfTransport(product.isSelfTransport())
                .variationCount(variationService.getVariationCountForProductId(product.getId())  != null ? variationService.getVariationCountForProductId(product.getId()) : null)
                .suggestedProductIdSlot1(product.getSuggestedProductIdSlot1())
                .suggestedProductIdSlot2(product.getSuggestedProductIdSlot2())
                .suggestedProductIdSlot3(product.getSuggestedProductIdSlot3())
                .suggestedProductIdSlot4(product.getSuggestedProductIdSlot4())
                .build();
    }

    public static ProductDTO entityToSafeDTO(Product product){
        return ProductDTO
                .builder()
                .name(product.getName())
                .description(product.getDescription())
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
                .vremeIsporukeDo(product.getVremeIsporukeDo())
                .vremeIsporukeOd(product.getVremeIsporukeOd())
                .sku(product.getSku())
                .slug(product.getSlug())
                .available(product.getAvailable())
                .selfTransport(product.isSelfTransport())
                .variationCount(variationService.getVariationCountForProductId(product.getId())  != null ? variationService.getVariationCountForProductId(product.getId()) : null)
                .suggestedProductIdSlot1(product.getSuggestedProductIdSlot1())
                .suggestedProductIdSlot2(product.getSuggestedProductIdSlot2())
                .suggestedProductIdSlot3(product.getSuggestedProductIdSlot3())
                .suggestedProductIdSlot4(product.getSuggestedProductIdSlot4())
                .build();
    }

    public static List<ProductDTO> listToDTOList(List<Product> products) {
        return products
                .stream()
                .map(product -> entityToDTO(product))
                .collect(Collectors.toList());
    }

}