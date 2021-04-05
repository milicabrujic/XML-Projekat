package com.main.app.controller.product;

import com.main.app.domain.dto.Entities;
import com.main.app.domain.dto.product.ProductDTO;
import com.main.app.domain.model.product.Product;
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

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;

import static com.main.app.converter.product.ProductConverter.DTOtoEntity;
import static com.main.app.converter.product.ProductConverter.entityToDTO;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductController {

    private final ProductService productService;

    private final VariationService variationService;

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
    @PostMapping(path = "/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProductDTO> add(@RequestBody @Valid ProductDTO productDTO) {
        Product product = productService.save(DTOtoEntity(productDTO));
        variationService.save(productDTO, product.getId());
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
        return new ResponseEntity<>(entityToDTO(productService.edit(DTOtoEntity(productDTO), id)), HttpStatus.OK);
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




}
