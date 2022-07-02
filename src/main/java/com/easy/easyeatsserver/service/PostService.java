package com.easy.easyeatsserver.service;

import com.easy.easyeatsserver.exception.PostNotExistException;
import com.easy.easyeatsserver.model.Post;
import com.easy.easyeatsserver.model.User;
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

    @Autowired
    public PostService(PostRepository postRepository, PostStorageService postStorageService) {
        this.postRepository = postRepository;
        this.postStorageService = postStorageService;
    }

    // upload
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void add(Post post, MultipartFile file) {
        String mediaLinks = postStorageService.save(file);
        post.setUrl(mediaLinks);
        this.postRepository.save(post);
    }

    // delete
    public void delete(int postId, String userEmail) throws PostNotExistException{
        Post post = findByIdAndUser(postId, userEmail);
        if (post == null) {
            throw new PostNotExistException("Post not exists");
        }
        this.postRepository.deleteById(postId);
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
