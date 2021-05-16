package com.main.app.elastic.repository.product;

import com.main.app.domain.dto.product.ProductSearchDTO;
import org.elasticsearch.index.query.BoolQueryBuilder;

public interface ProductElasticRepositoryBuilder {

    BoolQueryBuilder generateQuery(String filter, Long productType);

    BoolQueryBuilder generateSearchQuery(ProductSearchDTO productSearchDTO);

}
