package com.main.app.service.variation;

import com.main.app.domain.dto.Entities;
import com.main.app.domain.dto.product.ProductDTO;
import com.main.app.domain.dto.variation.VariationAttributeAttributeValueProductDTO;
import com.main.app.domain.model.attribute.Attribute;
import com.main.app.domain.model.attribute_value.AttributeValue;
import com.main.app.domain.model.image.Image;
import com.main.app.domain.model.product.Product;
import com.main.app.domain.model.product_attribute_values.ProductAttributeValues;
import com.main.app.domain.model.product_attributes.ProductAttributes;
import com.main.app.domain.model.variation.Variation;
import com.main.app.domain.model.variation_attribute_value_id.VariationAttributeValue;
import com.main.app.elastic.dto.variation.VariationElasticDTO;
import com.main.app.elastic.repository.variation.VariationElasticRepository;
import com.main.app.elastic.repository.variation.VariationElasticRepositoryBuilder;
import com.main.app.repository.image.ImageRepository;
import com.main.app.repository.product_attribute_values.ProductAttributeValuesRepository;
import com.main.app.repository.product_attributes.ProductAttributesRepository;
import com.main.app.repository.variation.VariationRepository;
import com.main.app.repository.variation_attribute_value_id.VariationAttributeValueRepository;
import com.main.app.service.attribute.AttributeService;
import com.main.app.service.attribute_value.AttributeValueService;
import com.main.app.util.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static com.main.app.converter.product.ProductConverter.DTOtoEntity;
import static com.main.app.converter.variation.VariationConverter.listToDTOList;
import static com.main.app.static_data.Messages.*;
import static com.main.app.util.MD5HashUtil.md5;
import static com.main.app.util.Util.variationsToIds;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VariationServiceImpl implements VariationService{

    @Value("${document.productcategory.path}")
    private String documentPath;

    private final VariationRepository variationRepository;

    private final VariationElasticRepository variationElasticRepository;

    private final VariationElasticRepositoryBuilder variationElasticRepositoryBuilder;

    private final AttributeValueService attributeValueService;

    private final AttributeService attributeService;

    private final ImageRepository imageRepository;

    private final VariationAttributeValueRepository variationAttributeValueRepository;

    private final ProductAttributesRepository productAttributesRepository;

    private final ProductAttributeValuesRepository productAttributeValuesRepository;

    private final EntityManager em;



    @Override
    public Entities getAll() {
        List<Variation> variationList = variationRepository.findAll();

        Entities entities = new Entities();
        entities.setEntities(listToDTOList(variationList));
        entities.setTotal(variationList.size());

        return entities;
    }

    @Override
    public Entities getAllBySearchParam(String searchParam, String productId, Pageable pageable) {
        Page<VariationElasticDTO> pagedVariations = variationElasticRepository.search(variationElasticRepositoryBuilder.generateQuery(searchParam, productId), pageable);

        List<Long> ids = variationsToIds(pagedVariations);

        Pageable mySqlPaging = PageRequest.of(0, pageable.getPageSize(), pageable.getSort());
        List<Variation> variations = variationRepository.findAllByIdIn(ids, mySqlPaging).getContent();

        Entities entities = new Entities();
        entities.setEntities(listToDTOList(variations));
        entities.setTotal(pagedVariations.getTotalElements());

        return entities;
    }

    @Override
    public Variation getOne(Long id) {
        return variationRepository.findOneById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, VARIATION_NOT_EXIST));

    }

    @Override
    public boolean save(ProductDTO productDTO, Long productId) {

        if(productDTO.getProductCategoryId() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, PRODUCT_PRODUCT_CATEGORY_CANT_BE_NULL);
        }
        if(productDTO.getBrandId() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, PRODUCT_BRAND_CANT_BE_NULL);
        }
        if(productDTO.getAttributeValueIds().keySet().size() == 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, PRODUCT_ATTRIBUTE_VALUES_CANT_BE_NULL);
        }

        Product product = DTOtoEntity(productDTO);
        product.setId(productId);

        List<List<String>> combined = new ArrayList<>();
        List<List<String>> combinedIds = new ArrayList<>();

        List<Long> productAttributeIds = new ArrayList<>();
        List<Long> productAttributeValueIds = new ArrayList<>();

        int numberOfAttributeIdsRepeating = 1;

        for (Long attrKey : productDTO.getAttributeValueIds().keySet()) {
            productAttributeIds.add(attrKey);
            List<String> attributeValueNames = new ArrayList<>();
            List<String> attributeValueIds = new ArrayList<>();

            for (Long attrValueKey : productDTO.getAttributeValueIds().get(attrKey)) {
                AttributeValue attributeValue = attributeValueService.getOne(attrValueKey);
                attributeValueNames.add(attributeValue.getName());
                attributeValueIds.add(String.valueOf(attributeValue.getId()));
                productAttributeValueIds.add(attributeValue.getId());
            }

            numberOfAttributeIdsRepeating *= attributeValueIds.size();

            combined.add(attributeValueNames);
            combinedIds.add(attributeValueIds);

        }

        List<Long> savedVariationsIds = new ArrayList<>();
        for(String attributeValueName : combinations(combined, 0)){
            String variationName = productDTO.getName() + "-" +
                    product.getProductCategory().getName() + "-" +
                    product.getBrand().getName() + "-" +
                    attributeValueName;

            Variation variation = new Variation();
            variation.setName(variationName);
            variation.setPrice(product.getPrice());
            variation.setProduct(product);

            Variation savedVariation = variationRepository.save(variation);
            variationElasticRepository.save(new VariationElasticDTO(savedVariation));
            savedVariationsIds.add(savedVariation.getId());
        }




        List<Long> savedVariationMultipliedIds = new ArrayList<>();
        for(Long savedVariationId : savedVariationsIds){
            for (int i = 0; i < productDTO.getAttributeValueIds().keySet().size(); i++) {
                savedVariationMultipliedIds.add(savedVariationId);
            }
        }

        List<Long> repeatedAttributeIds = new ArrayList<>();
        for(int i = 0; i< numberOfAttributeIdsRepeating; i++){
            for(Long attributeId : productDTO.getAttributeValueIds().keySet()){
                repeatedAttributeIds.add(attributeId);
            }
        }

        List<Long> variationsOfStringIds = new ArrayList<>();
        for(String oneStringVariation : combinations(combinedIds, 0)){
            String[] parts = oneStringVariation.split("-");
            for(int i = 0; i < parts.length; i++){
                variationsOfStringIds.add(Long.parseLong(parts[i]));
            }
        }




        for(int i = 0; i < savedVariationMultipliedIds.size(); i++){
            VariationAttributeValue variationAttributeValue = new VariationAttributeValue();

            Variation variation = variationRepository.getOne(savedVariationMultipliedIds.get(i));
            Attribute attribute = attributeService.getOne(repeatedAttributeIds.get(i));
            AttributeValue attributeValue = attributeValueService.getOne(variationsOfStringIds.get(i));

            variationAttributeValue.setVariation(variation);
            variationAttributeValue.setAttribute(attribute);
            variationAttributeValue.setAttributeValue(attributeValue);

            variationAttributeValueRepository.save(variationAttributeValue);
        }

        for(int i = 0; i < productAttributeValueIds.size(); i++){
            ProductAttributeValues productAttributeValues = new ProductAttributeValues();
            AttributeValue attributeValue = attributeValueService.getOne(productAttributeValueIds.get(i));

            productAttributeValues.setProduct(product);
            productAttributeValues.setAttributeValue(attributeValue);

            productAttributeValuesRepository.save(productAttributeValues);
        }

        return true;
    }

    @Override
    public Variation edit(Variation variation, Long id) {
        Optional<Variation> oneVariation = variationRepository.findOneByName(variation.getName());

        if(oneVariation.isPresent() && !id.equals(oneVariation.get().getId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, VARIATION_WITH_NAME_ALREADY_EXIST);
        }

        Optional<Variation> optionalVariation = variationRepository.findOneById(id);

        if(!optionalVariation.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, VARIATION_NOT_EXIST);
        }

        Variation foundVariation = optionalVariation.get();

        if(variation.getName() != null){
            foundVariation.setName(variation.getName());
        }

        foundVariation.setPrice(variation.getPrice());

        Variation savedVariation = variationRepository.save(foundVariation);
        variationElasticRepository.save(new VariationElasticDTO(savedVariation));

        return savedVariation;
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

            Variation variation = variationRepository.getOne(id);
            Image image = new Image();

            if(i == 0 && variation.getPrimaryImageUrl() == null){
                image.setPrimaryImage(true);
                variation.setPrimaryImageUrl("images/" + folderName + "/" + imageName + ".png");
                variationRepository.save(variation);
            }

            image.setVariation(variation);
            image.setName(imageName);
            image.setUrl("images/" + folderName + "/" + imageName + ".png");
            i++;

            imageRepository.save(image);
        }
    }

    @Override
    public Variation toggleActivate(Long id) {
        Optional<Variation> optionalVariation = variationRepository.findOneById(id);

        optionalVariation.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, VARIATION_NOT_EXIST));

        Variation foundVariation = optionalVariation.get();

        if(foundVariation.isActive()){
            foundVariation.setActive(false);
        }else{
            foundVariation.setActive(true);
        }

        Variation savedVariation = variationRepository.save(foundVariation);
        variationElasticRepository.save(ObjectMapperUtils.map(foundVariation, VariationElasticDTO.class));

        return savedVariation;
    }







    @Override
    public List<String> getAllAttributeNamesById(Long id) {
        List<VariationAttributeValue> variationAttributeValues = variationAttributeValueRepository.findAllByVariationIdAndDeletedFalse(id);
        List<String> variationAttributeNames = new ArrayList<>();

        for(VariationAttributeValue variationAttributeValue : variationAttributeValues){
            variationAttributeNames.add(variationAttributeValue.getAttribute().getName());
        }

        return variationAttributeNames;
    }

    @Override
    public List<String> getAllAttributeValueNamesById(Long id) {
        List<VariationAttributeValue> variationAttributeValues = variationAttributeValueRepository.findAllByVariationIdAndDeletedFalse(id);
        List<String> variationAttributeValueNames = new ArrayList<>();

        for(VariationAttributeValue variationAttributeValue : variationAttributeValues){
            variationAttributeValueNames.add(variationAttributeValue.getAttributeValue().getName());
        }

        return variationAttributeValueNames;
    }











    private String createDirectory() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH)+1;
        String folderName = "/" + month + "-" + year;

        new File(documentPath + folderName).mkdirs();

        return folderName;
    }


    private List<String> combinations(List<List<String>> arrays, int i) {
        if(i >= arrays.size()){
            return new ArrayList<>();
        }
        if (i == arrays.size() - 1) {
            return arrays.get(i);
        }

        // get combinations from subsequent arrays
        List<String> tmp = combinations(arrays, i+1);
        List<String> result = new ArrayList<>();

        // concat each array from tmp with each element from arrays[i]
        for (String v: arrays.get(i)) {
            for (String t: tmp) {
                result.add(v + '-' + t);
            }
        }

        return result;
    }



}
