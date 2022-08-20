package com.easy.easyeatsserver.controller;

import com.easy.easyeatsserver.model.Post;
import com.easy.easyeatsserver.model.PostType;
import com.easy.easyeatsserver.model.User;
import com.easy.easyeatsserver.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
public class PostController {
    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(value = "/posts")
    public List<Post> listAllPosts(Principal principal) {
        return this.postService.listByUser(principal.getName());
    }

    @PostMapping(value = "/posts")
    public void addImagePost(@RequestParam("message") String message,
                             @RequestParam("restaurant") String restaurant,
                             @RequestParam("type") String type,
                             @RequestParam("media_file") MultipartFile file,
                             Principal principal) {
        Post post = new Post();
        post.setUser(new User.Builder().setEmail(principal.getName()).build());
        post.setMessage(message);
        post.setRestaurant(restaurant);
        if (type.equals("image")) {
            post.setType(PostType.IMAGE);
        } else {
            post.setType(PostType.VIDEO);
        }
        this.postService.add(post, file);
    }

    @DeleteMapping(value = "/posts/{postId}")
    public void deletePost(@PathVariable int postId,
                           Principal principal) {
        this.postService.delete(postId, principal.getName());
    }


}
