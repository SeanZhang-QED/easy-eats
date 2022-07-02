package com.easy.easyeatsserver.controller;

import com.easy.easyeatsserver.model.Post;
import com.easy.easyeatsserver.model.PostType;
import com.easy.easyeatsserver.model.User;
import com.easy.easyeatsserver.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class PostController {
    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping(value = "/posts/upload")
    public void addImagePost(@RequestParam("user") String userEmail,
                             @RequestParam("message") String message,
                             @RequestParam("type") String type,
                             @RequestParam("media_file") MultipartFile file) {
        Post post = new Post();
        post.setUser(new User.Builder().setEmail(userEmail).build());
        post.setMessage(message);
        if (type.equals("image")) {
            post.setType(PostType.IMAGE);
        } else {
            post.setType(PostType.VIDEO);
        }
        this.postService.add(post, file);
    }

    @DeleteMapping(value = "/posts")
    public void deletePost(@RequestParam(name = "post_id") int postId,
                           @RequestParam(name = "user_email") String userEmail) {
        this.postService.delete(postId, userEmail);
    }

    @GetMapping(value = "/posts")
    public List<Post> listAllPosts(@RequestParam(name = "user_email") String userEmail) {
        return this.postService.listByUser(userEmail);
    }
}
