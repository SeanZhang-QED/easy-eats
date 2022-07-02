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

    public Token authenticate(User user, UserRole role) throws UserNotExistException {
        Authentication auth = null;
        try {
            auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        } catch (AuthenticationException exception) {
            throw new UserNotExistException("User Doesn't Exist");
        }

        if( !auth.getAuthorities().contains(new SimpleGrantedAuthority(role.name()))) {
            throw new UserHasNoAuthorityException("User has no authority to access");
        }
        return new Token(jwtUtil.generateToken(user.getEmail()));
    }

}
