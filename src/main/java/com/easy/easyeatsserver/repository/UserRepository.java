package com.easy.easyeatsserver.repository;

import com.easy.easyeatsserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    // 1. The type parameters for JpaRepository are User and String,
    // the first one corresponding to the name of the model class,
    // the second one corresponding to the ID type of the model class
    // 2. By extending the JpaRepository,
    // Spring can help provide some default implementations of common database operations(CRUD).
    // You can expose any of them and Spring will take care of the real implementation.
}
