package com.wildflix.wildflix.controllers;

import com.wildflix.wildflix.enums.RoleName;
import com.wildflix.wildflix.models.User;
import com.wildflix.wildflix.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/auth")
@RestController
public class AuthController {

    @Autowired
    UserService userService;


    @PostMapping("/sign-up-user")
    public ResponseEntity<User> createUser(@RequestBody User user){
        return
                new ResponseEntity<>(userService.createUser(user, RoleName.USER),
                        HttpStatus.CREATED);
    }

    @PostMapping("/sign-up-admin")
    public ResponseEntity<User> createAdmin(@RequestBody User user){
        return
            new ResponseEntity<>(userService.createUser(user, RoleName.ADMIN),
                    HttpStatus.CREATED);
    }
    @PostMapping("/login")
    ResponseEntity<String> login(@RequestBody Map<String, String> form){
        String response=userService.login(form.get("email"), form.get("password"));
            if (response == "user not found") {
                return new ResponseEntity<>(
                        "L'utilisateur n'est pas trouvé !",
                        HttpStatus.NOT_FOUND);
            } else
            if(response.equals("email not verified")) {
                return new ResponseEntity<>(
                        "Vous n'avez pas encore vérifié votre e-mail ! Veuillez vérifier votre e-mail.",
                        HttpStatus.UNAUTHORIZED
                );
            } else {
                return new ResponseEntity<>(
                        response,
                        HttpStatus.OK
                );
            }
        }


    @PostMapping("email-confirmation/{email}")
    boolean emailConfirmation(@PathVariable String email, @RequestBody Map<String, Integer> request){
        return userService.emailConfirmation(email, request.get("code"));
    }

}
