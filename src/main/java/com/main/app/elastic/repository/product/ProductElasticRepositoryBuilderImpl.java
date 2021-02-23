package com.main.app.elastic.repository.product;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.springframework.stereotype.Repository;

@Repository
public class ProductElasticRepositoryBuilderImpl implements ProductElasticRepositoryBuilder {

    @Override
    public BoolQueryBuilder generateQuery(String filter, Long productType) {
        filter = filter.toLowerCase();

        BoolQueryBuilder searchQuery = QueryBuilders.boolQuery();

        searchQuery.filter(QueryBuilders.termsQuery("deleted", "false"));

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();


        if(productType == 2){
            RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("productPosition");
            rangeQueryBuilder.from(0);
            rangeQueryBuilder.to(9);
            boolQuery.must(rangeQueryBuilder);
        }

        if(productType == 3){
            RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("discountProductPosition");
            rangeQueryBuilder.from(0);
            rangeQueryBuilder.to(9);
            boolQuery.must(rangeQueryBuilder);
        }

        boolQuery.must(
                new BoolQueryBuilder()
                        .should(QueryBuilders.wildcardQuery("name", "*" + filter + "*")));

        return searchQuery.must(boolQuery);
    }

}