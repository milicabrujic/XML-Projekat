package com.main.app.controller.product;

import com.main.app.converter.product_attributes.ProductAttributeConverter;
import com.main.app.domain.dto.Entities;
import com.main.app.domain.dto.product.ProductAttributeValueDTO;
import com.main.app.domain.dto.product.ProductDTO;
import com.main.app.domain.dto.product_attribute_category.ProductAttributeCategoryDTO;
import com.main.app.domain.dto.product_category.ProductCategoryDTO;
import com.main.app.domain.dto.product_prominent_attributes.ProductAttributesDTO;
import com.main.app.domain.model.product.Product;
import com.main.app.domain.model.product_category.ProductCategory;
import com.main.app.service.category.CategoryService;
import com.main.app.service.product.ProductService;
import com.main.app.service.variation.VariationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import static com.main.app.converter.product_attribute_category.ProductAttributeCategoryConverter.*;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static com.main.app.converter.product.ProductConverter.*;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductController {

    private final ProductService productService;

    private final VariationService variationService;

    private final CategoryService categoryService;

    @GetMapping(path="/all")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Entities<Product>> getAll(){
        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    }

    @GetMapping(path="/search")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Entities<Product>> getAllBySearchParam(Pageable pageable, @RequestParam(name = "searchParam") String searchParam,  @RequestParam(name = "productType") Long productType){
        return new ResponseEntity<>(productService.getAllBySearchParam(searchParam, productType, pageable), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProductDTO> getById(@PathVariable Long id) {
        return new ResponseEntity<>(entityToDTO(productService.getOne(id)), HttpStatus.OK);
    }

    @GetMapping(path = "/slug/{productSlug}")
    public ResponseEntity<ProductDTO> getBySlug(@PathVariable String productSlug) {
        return new ResponseEntity<>(entityToDTO(productService.getOneBySlug(productSlug)), HttpStatus.OK);
    }


    @PostMapping(path = "/")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProductDTO> add(@RequestBody @Valid ProductDTO productDTO) {
        Product product = productService.save(productDTO,DTOtoEntity(productDTO));

        if(productDTO.getAttributeValueIds().size() > 0){
            variationService.save(productDTO, product.getId());
        }
        return new ResponseEntity<>(entityToDTO(product), HttpStatus.OK);
    }

    @PostMapping(path = "/image/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void uploadImage(@PathVariable Long id, @RequestPart MultipartFile[] images) throws IOException {
        productService.uploadImage(id, images);
    }

    @PutMapping(path = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProductDTO> edit(@RequestBody ProductDTO productDTO, @PathVariable Long id) {
        return new ResponseEntity<>(entityToDTO(productService.edit(DTOtoEntity(productDTO),productDTO, id)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProductDTO> delete(@PathVariable Long id) {
        return new ResponseEntity<>(entityToDTO(productService.delete(id)), HttpStatus.OK);
    }

    @PutMapping(path = "/toggleactive/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProductDTO> toggleActive(@PathVariable Long id) {
        return new ResponseEntity<>(entityToDTO(productService.toggleActivate(id)), HttpStatus.OK);
    }


    @GetMapping(path = "/safe/{id}")
    public ResponseEntity<ProductDTO> getOneById(@PathVariable Long id) {
        return new ResponseEntity<>(entityToSafeDTO(productService.getOne(id)), HttpStatus.OK);
    }

    @GetMapping(path = "/attributevalues/{pid}")
    public ResponseEntity<List<ProductAttributeValueDTO>> getAllAttributeValuesForProductIdAndAttributeId(
            @PathVariable Long pid) {
        return new ResponseEntity<>(productService.getAllAttributeValuesForProductId(pid), HttpStatus.OK);
    }

    @GetMapping(path = "/attributesCategory/{id}")
    public ResponseEntity<List<ProductAttributeCategoryDTO>> getAllAttrCategForProductId(@PathVariable Long id){
        return new ResponseEntity<>(listToDTOList(productService.getAllAttributeCategoryForProduct(id)),HttpStatus.OK);
    }

    @GetMapping(path = "/prominent/{id}")
    public ResponseEntity<List<ProductAttributesDTO>> findAllByProductId(@PathVariable Long id){
        return new ResponseEntity<>(ProductAttributeConverter.listToDTOList(productService.findForProductId(id)),HttpStatus.OK);
    }

    @GetMapping(path = "/suggested/{id}")
    public ResponseEntity<List<ProductDTO>> getSuggestedProducts(@PathVariable Long id) {
        return new ResponseEntity<>(productService.getAllSuggestedProducts(id), HttpStatus.OK);
    }

    @GetMapping(path = "/possibly-available/{id}")
    public ResponseEntity<Integer> getPossiblyAvailable(@PathVariable Long id) {
        return new ResponseEntity<>(productService.getPossiblyAvailableForProductId(id), HttpStatus.OK);
    }


    @GetMapping(path = "/product-categories/{id}")
    public ResponseEntity<List<ProductCategoryDTO>> getCategoriesForProductId(@PathVariable Long id) {
        return new ResponseEntity<>(productService.getAllProductCategoriesForProductId(id), HttpStatus.OK);
    }


    @GetMapping(path = "/category/{id}")
    public ResponseEntity<List<ProductCategoryDTO>> getCategoriesForProduct(@PathVariable Long id) {
        return new ResponseEntity<>(productService.findByCategoryId(id), HttpStatus.OK);
    }

    @GetMapping(path="/find")
    public ResponseEntity<Entities<Product>> findAllBySearchParam(
            Pageable pageable,
            @RequestParam(name = "searchParam", required = false) String searchParam,
            @RequestParam(name = "category") String productCategoryId,
            @RequestParam(name = "filterCategoryValues") List<String> filterCategoryValues,
            @RequestParam(name = "filtersAttributeValues") List<String> filtersAttributeValues,
            @RequestParam(name = "findByNewAdded", required = false) boolean findByNewAdded){

        List<Long> productCategoryIds = new ArrayList<>();
        if(Long.valueOf(productCategoryId) != 0){
            productCategoryIds = categoryService.getAllSubCategories(Long.valueOf(productCategoryId));
        }
        if(productCategoryIds.size() == 0 && Long.valueOf(productCategoryId) != 0){
            productCategoryIds.add(Long.valueOf(productCategoryId));
        }

        List<Long> filterCategoryIds = new ArrayList<>();
        if(filterCategoryValues.size() > 0){
            for (String catId : filterCategoryValues) {
                List<Long> filterCategorySubIds = categoryService.getAllSubCategories(Long.valueOf(catId));
                if(filterCategoryIds != null){
                    for (Long subId : filterCategorySubIds) {
                        filterCategoryIds.add(subId);
                    }
                }
                if(filterCategoryIds.size() == 0){
                    filterCategoryIds.add(Long.valueOf(catId));
                }
            }
        }

        List<Long> attributeValuesFiltersIds = new ArrayList<>();
        if(filtersAttributeValues.size() > 0){
            for (String attrValId : filtersAttributeValues) {
                attributeValuesFiltersIds.add(Long.valueOf(attrValId));

            }
        }

        return new ResponseEntity<>(productService.findAllBySearchParam(searchParam, filterCategoryIds.size() > 0 ? filterCategoryIds : productCategoryIds, attributeValuesFiltersIds ,findByNewAdded,pageable), HttpStatus.OK);
    }

}
