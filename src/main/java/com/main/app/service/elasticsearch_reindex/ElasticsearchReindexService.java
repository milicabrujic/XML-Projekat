package com.main.app.service.elasticsearch_reindex;
import com.main.app.domain.model.attribute.Attribute;
import com.main.app.domain.model.attribute_value.AttributeValue;
import com.main.app.domain.model.brand.Brand;
import com.main.app.domain.model.category.Category;
import com.main.app.domain.model.user.User;
import com.main.app.elastic.dto.attribute.AttributeElasticDTO;
import com.main.app.elastic.dto.attribute_value.AttributeValueElasticDTO;
import com.main.app.elastic.dto.brand.BrandElasticDTO;
import com.main.app.elastic.dto.category.CategoryElasticDTO;
import com.main.app.elastic.dto.user.UserElasticDTO;
import com.main.app.elastic.repository.attribute.AttributeElasticRepository;
import com.main.app.elastic.repository.attribute_value.AttributeValueElasticRepository;
import com.main.app.elastic.repository.brand.BrandElasticRepository;
import com.main.app.elastic.repository.category.CategoryElasticRepository;
import com.main.app.elastic.repository.user.UserElasticRepository;
import com.main.app.repository.attribute.AttributeRepository;
import com.main.app.repository.attribute_value.AttributeValueRepository;
import com.main.app.repository.brand.BrandRepository;
import com.main.app.repository.category.CategoryRepository;
import com.main.app.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ElasticsearchReindexService {

    private Logger logger = LoggerFactory.getLogger(ElasticsearchReindexService.class);

    private final UserRepository userRepository;

    private final UserElasticRepository userElasticRepository;

    private final CategoryRepository categoryRepository;

    private final CategoryElasticRepository categoryElasticRepository;

    private final AttributeRepository attributeRepository;

    private final AttributeElasticRepository attributeElasticRepository;

    private final AttributeValueRepository attributeValueRepository;

    private final AttributeValueElasticRepository attributeValueElasticRepository;

    private final BrandRepository brandRepository;

    private final BrandElasticRepository brandElasticRepository;


    public void reindexAll(){
        reindexUser();
        reindexCategory();
        reindexAttribute();
        reindexAttributeValue();
        reindexBrand();
        logger.info("[RE-INDEX]Successfully re-indexed ALL");
    }

    public void reindexUser(){
        List<User> usersList = userRepository.findAllByDeletedFalse();
        usersList.forEach(user -> {
            userElasticRepository.save(new UserElasticDTO(user));
        });
        logger.info("[RE-INDEX]Successfully re-indexed USER");
    }

    public void reindexCategory(){
        List<Category> productCategoriesList = categoryRepository.findAll();
        productCategoriesList.forEach(productCategory -> {
            categoryElasticRepository.save(new CategoryElasticDTO(productCategory));
        });
        logger.info("[RE-INDEX]Successfully re-indexed CATEGORY");
    }

    public void reindexAttribute(){
        List<Attribute> attributeList = attributeRepository.findAll();
        attributeList.forEach(attribute -> {
            attributeElasticRepository.save(new AttributeElasticDTO(attribute));
        });
        logger.info("[RE-INDEX]Successfully re-indexed ATTRIBUTE");
    }

    public void reindexAttributeValue(){
        List<AttributeValue> attributeValueList = attributeValueRepository.findAll();
        attributeValueList.forEach(attributeValue -> {
            attributeValueElasticRepository.save(new AttributeValueElasticDTO(attributeValue));
        });
        logger.info("[RE-INDEX]Successfully re-indexed ATTRIBUTE VALUE");
    }

    public void reindexBrand(){
        List<Brand> brandList = brandRepository.findAll();
        brandList.forEach(brand -> {
            brandElasticRepository.save(new BrandElasticDTO(brand));
        });
        logger.info("[RE-INDEX]Successfully re-indexed BRAND");
    }
}
