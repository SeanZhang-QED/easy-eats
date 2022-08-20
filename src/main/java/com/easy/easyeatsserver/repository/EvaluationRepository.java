package com.easy.easyeatsserver.repository;

import com.easy.easyeatsserver.model.Evaluation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationRepository extends ElasticsearchRepository<Evaluation, Integer>, CustomEvaluationRepository {
}
