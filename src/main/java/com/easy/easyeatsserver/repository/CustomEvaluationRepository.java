package com.easy.easyeatsserver.repository;

import com.easy.easyeatsserver.model.Post;

import java.util.List;

public interface CustomEvaluationRepository {
    List<Integer> matchByKeyword(String keyword);
}
