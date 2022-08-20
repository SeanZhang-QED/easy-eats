package com.easy.easyeatsserver.service;

import com.easy.easyeatsserver.model.Post;
import com.easy.easyeatsserver.model.User;
import com.easy.easyeatsserver.repository.EvaluationRepository;
import com.easy.easyeatsserver.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {
    private PostRepository postRepository;
    private EvaluationRepository evaluationRepository;

    @Autowired
    public SearchService(PostRepository postRepository, EvaluationRepository evaluationRepository) {
        this.postRepository = postRepository;
        this.evaluationRepository = evaluationRepository;
    }

    public List<Post> searchPostByEmail(String email) {
        return this.postRepository.findByUser(new User.Builder().setEmail(email).build());
    }

    public List<Post> searchPostByKeyword(String keywords) {
        if (keywords == null) {
            return this.postRepository.findAll();
        }
//        evaluationRepository.matchByKeyword(keywords);
        return this.postRepository.findByIdIn(evaluationRepository.matchByKeyword(keywords));
    }

    public List<Post> searchPostByRestaurant(String restaurant) {
        if (restaurant == null) {
            return this.postRepository.findAll();
        }
//        evaluationRepository.matchByRestaurant(restaurant);
        return this.postRepository.findByIdIn(evaluationRepository.matchByRestaurant(restaurant));
    }
}
