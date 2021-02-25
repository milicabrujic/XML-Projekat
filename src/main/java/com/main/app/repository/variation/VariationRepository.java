package com.main.app.repository.variation;

import com.main.app.domain.model.variation.Variation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface VariationRepository extends JpaRepository<Variation, Long> {

    Page<Variation> findAllByIdIn(List<Long> idsList, Pageable pageable);

    Optional<Variation> findOneByName(String name);

    Optional<Variation> findOneById(Long id);

    List<Variation> findAllByProductId(Long productId);

}
