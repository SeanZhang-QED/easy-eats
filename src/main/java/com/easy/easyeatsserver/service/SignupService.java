package com.easy.easyeatsserver.service;

import com.easy.easyeatsserver.exception.UserAlreadyExistException;
import com.easy.easyeatsserver.model.Authority;
import com.easy.easyeatsserver.model.Cart;
import com.easy.easyeatsserver.model.User;
import com.easy.easyeatsserver.repository.AuthorityRepository;
import com.easy.easyeatsserver.repository.CartRepository;
import com.easy.easyeatsserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SignupService {
    private UserRepository userRepository;
    private AuthorityRepository authorityRepository;
    private CartRepository cartRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired // better autowired method than member field
    public SignupService(UserRepository userRepository, AuthorityRepository authorityRepository, CartRepository cartRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.cartRepository = cartRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE) // all or nothing(roll back)
    public void add(User user) {
        // step 1: to deal with the duplicated email
        // We need to throw an exception, which would be caught and handled by controller.
        if(userRepository.existsById(user.getEmail())) { // user's PK is email, not username
            throw new UserAlreadyExistException("User already exists.");
        }
        // step 2: encrypt with the help of Spring security
        user.setPassword(passwordEncoder.encode(user.getPassword()));// encrypt user's password
        user.setEnabled(true);
        // step 3: execute on database
        cartRepository.save(new Cart(user.getEmail(), 0));
        user.setCart(cartRepository.getReferenceById(user.getEmail()));
        userRepository.save(user);
        authorityRepository.save(new Authority(user.getEmail(), "Customer"));
    }
}
