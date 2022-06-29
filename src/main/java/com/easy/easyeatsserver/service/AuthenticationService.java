package com.easy.easyeatsserver.service;

import com.easy.easyeatsserver.exception.UserHasNoAuthorityException;
import com.easy.easyeatsserver.exception.UserNotExistException;
import com.easy.easyeatsserver.model.Token;
import com.easy.easyeatsserver.model.User;
import com.easy.easyeatsserver.model.UserRole;
import com.easy.easyeatsserver.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;

    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public Token authenticate(User user, UserRole userRole) throws UserNotExistException, UserHasNoAuthorityException {
        Authentication auth = null;
        try {
            auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        } catch (AuthenticationException ex) {
            throw new UserNotExistException("User not exist or entering wrong password");
        }

        if (auth == null || !auth.isAuthenticated()) {
            // fail to authenticate
            throw new UserNotExistException("User not exist or entering wrong password");
        }

        if(!auth.getAuthorities().contains(new SimpleGrantedAuthority(userRole.name()))) {
            // current user does not permit to access this endpoint
            throw new UserHasNoAuthorityException("User does not have the authority to access.");
        }

        return new Token(jwtUtil.generateToken(user.getEmail()));
    }

}
