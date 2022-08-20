package com.easy.easyeatsserver.service;

import com.easy.easyeatsserver.exception.PostNotExistException;
import com.easy.easyeatsserver.model.Evaluation;
import com.easy.easyeatsserver.model.Post;
import com.easy.easyeatsserver.model.User;
import com.easy.easyeatsserver.repository.EvaluationRepository;
import com.easy.easyeatsserver.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class PostService {
    private PostRepository postRepository;
    private PostStorageService postStorageService;
    private EvaluationRepository evaluationRepository;

    @Autowired
    public PostService(PostRepository postRepository, PostStorageService postStorageService, EvaluationRepository evaluationRepository) {
        this.postRepository = postRepository;
        this.postStorageService = postStorageService;
        this.evaluationRepository = evaluationRepository;
    }

    // upload
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void add(Post post, MultipartFile file) {
        // Step 1: save the post image to GCS, get the url
        String mediaLinks = postStorageService.save(file);
        // Step 2: save the post to MySQL
        post.setUrl(mediaLinks);
        this.postRepository.save(post);
        // Step 3: save the post to ES
        this.evaluationRepository.save(new Evaluation(post.getId(), post.getMessage(), post.getRestaurant()));
    }

    // delete
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void delete(int postId, String userEmail) throws PostNotExistException{
        Post post = findByIdAndUser(postId, userEmail);
        if (post == null) {
            throw new PostNotExistException("Post not exists");
        }
        this.postRepository.deleteById(postId);
        this.evaluationRepository.deleteById(postId);
    }

    // list user's all posts
    public List<Post> listByUser(String userEmail) {
        return this.postRepository.findByUser(new User.Builder().setEmail(userEmail).build());
    }

    // find the post by id
    public Post findByIdAndUser(int postId, String userEmail) throws PostNotExistException{
        Post post = this.postRepository.findByIdAndUser(postId, new User.Builder().setEmail(userEmail).build());
        if(post == null) {
            throw new PostNotExistException("Post not exists");
        }
        return post;
    }
}
