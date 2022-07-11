package com.easy.easyeatsserver.repository;

import com.easy.easyeatsserver.model.Evaluation;
import com.easy.easyeatsserver.model.Post;
import org.elasticsearch.index.query.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;

import java.util.ArrayList;
import java.util.List;

public class CustomEvaluationRepositoryImpl implements CustomEvaluationRepository {
    private ElasticsearchOperations elasticsearchOperations;

    @Autowired
    public CustomEvaluationRepositoryImpl(ElasticsearchOperations elasticsearchOperations, PostRepository postRepository) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @Override
    public List<Integer> matchByKeyword(String keywords) {
        // step 1: build the query
        Query query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("message", keywords).operator(Operator.AND))
                .build();

        // Step 2: do the search
        SearchHits<Evaluation> searchResult = elasticsearchOperations.search(query, Evaluation.class);
        List<Integer> results = new ArrayList<>();
        for (SearchHit<Evaluation> hit : searchResult.getSearchHits()) {
            results.add(hit.getContent().getId());
        }
        return results;
    }
}
