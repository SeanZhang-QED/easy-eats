package com.easy.easyeatsserver.controller;

import com.easy.easyeatsserver.model.Token;
import com.easy.easyeatsserver.model.User;
import com.easy.easyeatsserver.model.UserRole;
import com.easy.easyeatsserver.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    private AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public Token authenticateUser(@RequestBody User user) {
        return authenticationService.authenticate(user, UserRole.CUSTOMER);
    }
}
