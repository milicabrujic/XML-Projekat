package com.main.app.repository.product;

import com.main.app.domain.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findBySlug(String slug);

    Long countBySlug(String slug);

    Page<Product> findAllByIdIn(List<Long> idsList, Pageable pageable);

    Optional<Product> findOneByNameAndDeletedFalse(String name);

    List<Product> findAllByNameAndDeletedFalse(String name);

    Optional<Product> findOneById(Long id);

    List<Product> findAllByBrandId(Long Id);

    List<Product> findAllByProductCategoryId(Long id);

    Optional<Product> findOneByProductPosition(Long id);

    Optional<Product> findOneByDiscountProductPosition(Long id);

}
