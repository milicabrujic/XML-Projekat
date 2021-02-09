package com.main.app.service.product;

import com.main.app.domain.dto.Entities;
import com.main.app.domain.dto.product.ProductAttributeAttrValueDTO;
import com.main.app.domain.model.image.Image;
import com.main.app.domain.model.product.Product;
import com.main.app.domain.model.product_attribute_values.ProductAttributeValues;
import com.main.app.domain.model.product_attributes.ProductAttributes;
import com.main.app.elastic.dto.product.ProductElasticDTO;
import com.main.app.elastic.repository.product.ProductElasticRepository;
import com.main.app.elastic.repository.product.ProductElasticRepositoryBuilder;
import com.main.app.repository.image.ImageRepository;
import com.main.app.repository.product.ProductAttributeAttrValueRepository;
import com.main.app.repository.product.ProductRepository;
import com.main.app.repository.product_attribute_values.ProductAttributeValuesRepository;
import com.main.app.repository.product_attributes.ProductAttributesRepository;
import com.main.app.util.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static com.main.app.converter.product.ProductConverter.listToDTOList;
import static com.main.app.static_data.Messages.*;
import static com.main.app.util.MD5HashUtil.md5;
import static com.main.app.util.Util.productsToIds;


@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductServiceImpl implements ProductService {

    @Value("${document.productcategory.path}")
    private String documentPath;

    private final ProductRepository productRepository;

    private final ProductElasticRepository productElasticRepository;

    private final ProductElasticRepositoryBuilder productElasticRepositoryBuilder;

    private final ImageRepository imageRepository;

    private final ProductAttributesRepository productAttributesRepository;

    private final ProductAttributeValuesRepository productAttributeValuesRepository;

    private final ProductAttributeAttrValueRepository productAttributeAttrValueRepository;


    @Override
    public Entities getAll() {
        List<Product> productList = productRepository.findAll();

        Entities entities = new Entities();
        entities.setEntities(listToDTOList(productList));
        entities.setTotal(productList.size());

        return entities;
    }

    @Override
    public Entities getAllBySearchParam(String searchParam, Long productType, Pageable pageable) {
        Page<ProductElasticDTO> pagedProducts = productElasticRepository.search(productElasticRepositoryBuilder.generateQuery(searchParam, productType), pageable);

        List<Long> ids = productsToIds(pagedProducts);

        Pageable mySqlPaging = null;
        if(productType == 2){
            mySqlPaging = PageRequest.of(0, pageable.getPageSize(), Sort.by("productPosition"));
        }else if(productType == 3){
            mySqlPaging = PageRequest.of(0, pageable.getPageSize(), Sort.by("discountProductPosition"));
        }else{
            mySqlPaging = PageRequest.of(0, pageable.getPageSize(), pageable.getSort());
        }

        List<Product> products = productRepository.findAllByIdIn(ids, mySqlPaging).getContent();

        Entities entities = new Entities();
        entities.setEntities(listToDTOList(products));
        entities.setTotal(pagedProducts.getTotalElements());

        return entities;
    }

    @Override
    public Product getOne(Long id) {
        return productRepository.getOne(id);
    }

    @Override
    public Product save(Product product) {
        Optional<Product> oneProduct = productRepository.findOneByNameAndDeletedFalse(product.getName());

        if(oneProduct.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, PRODUCT_WITH_NAME_ALREADY_EXIST);
        }

        if(product.getName() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, PRODUCT_NAME_CANT_BE_NULL);
        }

        if(product.getProductPosition() != null) {
            Optional<Product> sameProductPosition = productRepository.findOneByProductPosition(product.getProductPosition());
            if(sameProductPosition.isPresent()){
                Product foundedSameProductPosition = sameProductPosition.get();
                foundedSameProductPosition.setProductPosition(null);
                Product savedSameProductPosition = productRepository.save(foundedSameProductPosition);
                productElasticRepository.save(new ProductElasticDTO(savedSameProductPosition));
            }
        }

        if(product.getDiscountProductPosition() != null) {
            Optional<Product> sameDiscountProductPosition = productRepository.findOneByDiscountProductPosition(product.getDiscountProductPosition());
            if (sameDiscountProductPosition.isPresent()) {
                Product foundedSameDiscountProductPosition = sameDiscountProductPosition.get();
                foundedSameDiscountProductPosition.setDiscountProductPosition(null);
                Product savedSameDiscountProductPosition = productRepository.save(foundedSameDiscountProductPosition);
                productElasticRepository.save(new ProductElasticDTO(savedSameDiscountProductPosition));
            }
        }

        Product savedProduct = productRepository.save(product);
        productElasticRepository.save(new ProductElasticDTO(savedProduct));

        return savedProduct;
    }

    @Override
    public Product edit(Product product, Long id) {
        Optional<Product> oneProduct = productRepository.findOneByNameAndDeletedFalse(product.getName());

        if(product.getProductPosition() != null) {
            Optional<Product> sameProductPosition = productRepository.findOneByProductPosition(product.getProductPosition());
            if (sameProductPosition.isPresent()) {
                Product foundedSameProductPosition = sameProductPosition.get();
                foundedSameProductPosition.setProductPosition(null);
                Product savedSameProductPosition = productRepository.save(foundedSameProductPosition);
                productElasticRepository.save(new ProductElasticDTO(savedSameProductPosition));
            }
        }
        if(product.getDiscountProductPosition() != null) {
            Optional<Product> sameDiscountProductPosition = productRepository.findOneByDiscountProductPosition(product.getDiscountProductPosition());
            if (sameDiscountProductPosition.isPresent()) {
                Product foundedSameDiscountProductPosition = sameDiscountProductPosition.get();
                foundedSameDiscountProductPosition.setDiscountProductPosition(null);
                Product savedSameDiscountProductPosition = productRepository.save(foundedSameDiscountProductPosition);
                productElasticRepository.save(new ProductElasticDTO(savedSameDiscountProductPosition));
            }
        }

        if(oneProduct.isPresent() && !id.equals(oneProduct.get().getId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, PRODUCT_WITH_NAME_ALREADY_EXIST);
        }

        Optional<Product> optionalProduct = productRepository.findOneById(id);

        optionalProduct.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, PRODUCT_NOT_EXIST));

        Product foundProduct = optionalProduct.get();

        if(product.getName() != null){
            foundProduct.setName(product.getName());
        }

        foundProduct.setProductCategory(product.getProductCategory());
        foundProduct.setDescription(product.getDescription());
        foundProduct.setBrand(product.getBrand());
        foundProduct.setDiscount(product.getDiscount());
        foundProduct.setProductPosition(product.getProductPosition());
        foundProduct.setDiscountProductPosition(product.getDiscountProductPosition());
        foundProduct.setPrice(product.getPrice());

        Product savedProduct = productRepository.save(foundProduct);
        productElasticRepository.save(new ProductElasticDTO(savedProduct));

        return savedProduct;
    }

    @Override
    public Product delete(Long id) {
        Optional<Product> optionalProduct = productRepository.findOneById(id);

        optionalProduct.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, PRODUCT_NOT_EXIST));

        Product foundProduct = optionalProduct.get();
        foundProduct.setDeleted(true);
        foundProduct.setDateDeleted(Calendar.getInstance().toInstant());

        Product savedProduct = productRepository.save(foundProduct);
        productElasticRepository.save(ObjectMapperUtils.map(foundProduct, ProductElasticDTO.class));

        return savedProduct;
    }

    @Override
    public void uploadImage(Long id, MultipartFile[] images) throws IOException {
        int i = 0;
        for(MultipartFile uploadedFile : images) {
            String folderName = this.createDirectory().split("/")[1];
            String imageName = md5(uploadedFile.getOriginalFilename());

            byte[] bytes = uploadedFile.getBytes();

            Path path = Paths.get(documentPath + folderName + "/" + imageName  + ".png");
            Files.write(path, bytes);

            Product product = productRepository.getOne(id);
            Image image = new Image();

            if(i == 0 && product.getPrimaryImageUrl() == null){
               image.setPrimaryImage(true);
               product.setPrimaryImageUrl("images/" + folderName + "/" + imageName + ".png");
               productRepository.save(product);
            }

            image.setProduct(product);
            image.setName(imageName);
            image.setUrl("images/" + folderName + "/" + imageName + ".png");
            i++;

            imageRepository.save(image);
        }
    }

    @Override
    public Product toggleActivate(Long id) {
        Optional<Product> optionalProduct = productRepository.findOneById(id);

        optionalProduct.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, PRODUCT_NOT_EXIST));

        Product foundProduct = optionalProduct.get();

        if(foundProduct.isActive()){
            foundProduct.setActive(false);
//            if(!productVariations.isEmpty()){
//                for(Variation variation : productVariations){
//                    variation.setActive(false);
//                    variationRepository.save(variation);
//                }
//            }
        }else{
            foundProduct.setActive(true);
//            if(!productVariations.isEmpty()){
//                for(Variation variation : productVariations){
//                    variation.setActive(true);
//                    variationRepository.save(variation);
//                }
//            }
        }
        Product savedProduct = productRepository.save(foundProduct);
        productElasticRepository.save(ObjectMapperUtils.map(foundProduct, ProductElasticDTO.class));

        return savedProduct;
    }

    @Override
    public void checkIfHasForeignKey(Long id, String entity) {
        List<Product> products = productRepository.findAll();
        if(entity.equals("brand")){
            for(Product product : products){
                if(product.getBrand().getId().equals(id)){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, BRAND_IN_PRODUCT_EXISTS);
                }
            }
        }else if(entity.equals("category")){
            for(Product product : products){
                if(product.getProductCategory().getId().equals(id)){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, PRODUCT_CATEGORY_IN_PRODUCT_EXISTS);
                }
            }
        }else if(entity.equals("attribute")){
            List<ProductAttributes> productAttributesList = productAttributesRepository.findAllByAttributeIdAndDeletedFalse(id);
            if(productAttributesList.size() != 0){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ATTRIBUTE_IN_PRODUCT_EXISTS);
            }
        }else if(entity.equals("attribute_value")){
            List<ProductAttributeValues> productAttributeValues = productAttributeValuesRepository.findAllByAttributeValueIdAndDeletedFalse(id);
            if(productAttributeValues.size() != 0){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ATTRIBUTE_VALUE_IN_PRODUCT_EXISTS);
            }
        }
    }


    @Override
    public List<ProductAttributeAttrValueDTO> getAllAttributeValuesForProductId(Long productId) {
        return productAttributeAttrValueRepository.findAllAttributeValuesForProductId(productId);
    }




    private String createDirectory() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH)+1;

        String folderName = "/" + month + "-" + year;

        new File(documentPath + folderName).mkdirs();

        return folderName;
    }

}
