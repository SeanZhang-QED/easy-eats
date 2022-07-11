package com.easy.easyeatsserver.repository;

import com.easy.easyeatsserver.model.Post;
import com.easy.easyeatsserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByUser(User user); // select * where post.user = user

    Post findByIdAndUser(int id, User user); // select * where post.id = id and stay.user = user

    List<Post> findByIdIn(List<Integer> ids);
}
