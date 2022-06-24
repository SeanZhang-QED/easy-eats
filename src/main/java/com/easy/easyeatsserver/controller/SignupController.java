package com.easy.easyeatsserver.controller;

import com.easy.easyeatsserver.model.User;
import com.easy.easyeatsserver.service.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// @Controller + @ResponseBody
@RestController
// A special controller used in RESTful Web services,
// and it's the combination of @Controller and @ResponseBody.
// => will return JSON as response data type.
public class SignupController {
    private SignupService signupService;

    @Autowired
    public SignupController(SignupService signupService) {
        this.signupService = signupService;
    }

    @PostMapping("/signup") // indicate the API supports POST method and maps to the /signup path.
    public void addUser(@RequestBody User user) { // help convert the request body from JSON format to a Java object.
        // Handle Exception:
        // AOP: We can definitely handle that inside of addGuest and addHost.
        // But a cleaner way is to have a dedicated exception handler to
        // handle all kinds of exceptions for every controller.
        // Exception:
        // service => controller => exception handler
        signupService.add(user);
    }
}
