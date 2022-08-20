package com.easy.easyeatsserver.controller;

import com.easy.easyeatsserver.model.Post;
import com.easy.easyeatsserver.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchController {
    private SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping(value = "/search")
    public List<Post> searchPosts(@RequestParam(value = "user", required = false) String email,
                                  @RequestParam(value = "restaurant", required = false) String restaurant,
                                  @RequestParam(value = "keywords", required = false) String keywords ) {
        if (email != null) {
            return this.searchService.searchPostByEmail(email);
        }
        if(restaurant != null) {
            return this.searchService.searchPostByRestaurant(restaurant);
        }
        return this.searchService.searchPostByKeyword(keywords);
    }
}
