package com.main.app.repository.product_category;

import com.main.app.domain.model.product_category.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Long> {

    List<ProductCategory> findAllByProductId(Long product_id);

    Optional<ProductCategory> findByCategoryId(Long category_id);

}
