package com.main.app.elastic.repository.product;

import com.main.app.domain.dto.product.ProductSearchDTO;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.springframework.stereotype.Repository;

import static org.elasticsearch.index.query.QueryBuilders.nestedQuery;

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
                        .should(QueryBuilders.wildcardQuery("name", "*" + filter + "*"))
                        .should(QueryBuilders.wildcardQuery("sku", "*" + filter + "*"))
                        .should(QueryBuilders.wildcardQuery("slug", "*" + filter + "*"))
                        .should(QueryBuilders.wildcardQuery("brandName", "*" + filter + "*"))
//                        .should(QueryBuilders.wildcardQuery("categoryName", "*" + filter + "*"))
        );

        return searchQuery.must(boolQuery);
    }


    @Override
    public BoolQueryBuilder generateSearchQuery(ProductSearchDTO productSearchDTO) {
        String filter = productSearchDTO.getSearchParam() != null ? productSearchDTO.getSearchParam().toLowerCase() : "";

        BoolQueryBuilder searchQuery = QueryBuilders.boolQuery();

        searchQuery.filter(QueryBuilders.termsQuery("deleted", "false"));

        searchQuery.filter(QueryBuilders.termsQuery("active", "true"));

        if(productSearchDTO.isFindByNewAdded()){
            searchQuery.filter(QueryBuilders.termsQuery("newAdded", "true"));
        }

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        boolQuery.must(
                new BoolQueryBuilder()
                        .should(QueryBuilders.wildcardQuery("name", "*" + filter + "*"))
                        .should(QueryBuilders.fuzzyQuery("name", filter).fuzziness(Fuzziness.TWO).transpositions(true))
                        .should(QueryBuilders.wildcardQuery("description", "*" + filter + "*"))
        );

        if(productSearchDTO.getProductCategoryIds().size() > 0){
                searchQuery.filter(nestedQuery("productCategories", QueryBuilders.boolQuery().must(QueryBuilders.termsQuery("productCategories.categoryId", productSearchDTO.getProductCategoryIds())), ScoreMode.None));
        }


        return searchQuery.must(boolQuery);
    }


}
