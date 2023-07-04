package com.wildflix.wildflix.controllers;

import com.wildflix.wildflix.enums.RoleName;
import com.wildflix.wildflix.models.User;
import com.wildflix.wildflix.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RequestMapping("/auth")
@RestController
public class AuthController {

    @Autowired
    UserService userService;


    @PostMapping("/sign-up-user")
    public void createUser(@RequestBody User user){
        User result = userService.createUser(user);
        userService.addRoleToUser(result.getEmail(),RoleName.USER);
    }

    @PostMapping("/sign-up-admin")
    public void createAdmin(@RequestBody User user){
        User result = userService.createUser(user);
        userService.addRoleToUser(result.getEmail(), RoleName.ADMIN);
    }
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> request){
        return userService.login(request.get("email"), request.get("password"));
    }
}
