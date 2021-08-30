package com.example.test01.dao.impl;

import com.example.test01.dao.ServiceCaseDao;
import com.example.test01.dto.ServiceCase;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.MultiGetItem;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceCaseDaoImpl implements ServiceCaseDao {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public boolean indexExists() {
        IndexCoordinates index = IndexCoordinates.of("users");
        return elasticsearchRestTemplate.exists("users", index);
    }

    @Override
    public void saveServiceCases(List<ServiceCase> serviceCases){
        elasticsearchRestTemplate.save(serviceCases);
    }

    @Override
    public List<ServiceCase> selectServiceCase(String field, String value){
        // 组装Builder
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        // 关键词筛选
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // 模糊匹配
        boolQueryBuilder.should(QueryBuilders.matchPhrasePrefixQuery(field, value));

        // 精准匹配
        /*boolQueryBuilder.should(QueryBuilders.matchPhraseQuery("productName.keyword", keyword));
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);

        // 分组。terms分组名称、field分组字段、size分组数量
        TermsAggregationBuilder builder = AggregationBuilders.terms("firstCategoryId").field("firstCategoryId").size(9999);
        nativeSearchQueryBuilder.addAggregation(builder);

        // 数据分页。需求是聚合，数据本身我并不需要，所以只返回一条数据就行
        Pageable pageable = PageRequest.of(0, 1);
        nativeSearchQueryBuilder.withPageable(pageable);
        */

        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
        nativeSearchQueryBuilder.withHighlightFields(new HighlightBuilder.Field(field));

        SearchHits<ServiceCase> sh = elasticsearchRestTemplate.search(nativeSearchQueryBuilder.build(),ServiceCase.class);
        return sh.stream().map(SearchHit::getContent).collect(Collectors.toList());
    }
}
