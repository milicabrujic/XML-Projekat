package com.main.app.service.elasticsearch_reindex;
import com.main.app.domain.model.category.Category;
import com.main.app.domain.model.user.User;
import com.main.app.elastic.dto.category.CategoryElasticDTO;
import com.main.app.elastic.dto.user.UserElasticDTO;
import com.main.app.elastic.repository.category.CategoryElasticRepository;
import com.main.app.elastic.repository.user.UserElasticRepository;
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

    public void reindexAll(){
        reindexUser();
        reindexCategory();
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


}
