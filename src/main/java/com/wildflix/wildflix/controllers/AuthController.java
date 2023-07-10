package com.wildflix.wildflix.controllers;

import com.wildflix.wildflix.DTOs.UserDTO;
import com.wildflix.wildflix.enums.RoleName;
import com.wildflix.wildflix.exceptions.JWTException;
import com.wildflix.wildflix.exceptions.UserNotFound;
import com.wildflix.wildflix.models.User;
import com.wildflix.wildflix.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/auth")
@RestController
public class AuthController {

    @Autowired
    UserService userService;


    @PostMapping("/sign-up-user")
    public ResponseEntity<?> createUser(@RequestBody User user){
        User result = userService.createUser(user, RoleName.USER);
        //result = userService.addRoleToUser(result.getEmail(), RoleName.USER);

        if (result != null) {
            List<String> roles = new ArrayList<>();
            result.getRoles().forEach(
                    role-> roles.add(role.getName().name())
            );
            return
                    new ResponseEntity<>(
                            RegisterResponse
                                    .builder()
                                    .email(result.getEmail()).build(),
                            HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>("Une erreur est survenu !", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/sign-up-admin")
    public ResponseEntity<User> createAdmin(@RequestBody User user){
        return
            new ResponseEntity<>(userService.createUser(user, RoleName.ADMIN),
                    HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> request){
        String response = userService.login(request.get("email"), request.get("password"));
       if(response==null){
           return new ResponseEntity<>(
                   "Email ou le mot de passe sont incorrects ",
                   HttpStatus.UNAUTHORIZED
           );
       }else
        if(response.equals("-1")){
            return new ResponseEntity<>(
                    "Vous n'avez pas encore vérifié votre email ! ",
                    HttpStatus.UNAUTHORIZED
            );
        }else{
            return new ResponseEntity<String>(
                   // LoginResponse.builder().jwt(response).build(),
                    HttpStatus.OK
            );
        }
    }

    @PostMapping("/email-confirmation/{email}")
    public ResponseEntity<?> emailConfirmation(@PathVariable String email, @RequestBody Map<String, Integer> request) throws UserNotFound {
        Map<String, Object> body = new HashMap<>();
        try {


            if(userService.emailConfirmation(email, request.get("code"))) {
                if(Boolean.valueOf(userService.emailConfirmation(email, request.get("code"))))
                {
                body.put("message","Code correct ! email confirmed , You will redirect to the login page");
                return new ResponseEntity<>(body, HttpStatus.OK);
                }
                else
                {
                    body.put("message","the code must be an integer !");
                    return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
                }
            }
            else{
                body.put("message","incorrect code, please try again !");
                return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);

            }
        }catch (UserNotFound e){
            body.put("message","User not found !");
    return new ResponseEntity<>(
            body,
             HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/reset-password-request")
    ResponseEntity<?> resetPasswordRequest(@RequestBody Map<String, String> request){
        Map<String, Object> body = new HashMap<>();

        try{
            if(userService.resetPasswordRequest(request.get("email"))){
                body.put("message", "A reset email has been sent to you !");
                return new ResponseEntity<>(
                        body,
                        HttpStatus.OK
                );
            }else{
                body.put("message", "An error occurred, try again !");
                return new ResponseEntity<>(
                        body,
                        HttpStatus.INTERNAL_SERVER_ERROR
                );
            }

        }catch(UserNotFound e){
            body.put("message", "User not found !");
            return new ResponseEntity<>(
                    body,
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @PutMapping("/reset-password")
    ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request){
        Map<String, Object> body = new HashMap<>();
        try{
            userService.resetPassword(request.get("token"), request.get("password"));
            body.put("message","The password has been changed");
            return new ResponseEntity<>(
                    body,
                    HttpStatus.OK
            );
        }catch(UserNotFound e){
            body.put("Message", "User not found !");
            return new ResponseEntity<>(
                    body,
                    HttpStatus.NOT_FOUND
            );
        }catch(JWTException e){

            body.put("Message", "The link is invalid, try again");
            return new ResponseEntity<>(
                    body,
                    HttpStatus.UNAUTHORIZED
            );

        }
    }



}
