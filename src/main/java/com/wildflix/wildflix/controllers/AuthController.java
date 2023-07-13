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
        Map<String, Object> body = new HashMap<>();

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
            body.put("message", "Une erreur est survenu !");
            return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/sign-up-admin")
    public ResponseEntity<User> createAdmin(@RequestBody User user){
        return
            new ResponseEntity<>(userService.createUser(user, RoleName.ADMIN),
                    HttpStatus.CREATED);
    }
    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody Map<String, String> form){
        String response=userService.login(form.get("email"), form.get("password"));
        Map<String, Object> body = new HashMap<>();

        if (response.equals("user not found")) {
            body.put("message","L'utilisateur n'est pas trouvé ! Vérifiez votre adresse mail et votre mot de passe.");
            return new ResponseEntity<>(
                    body,
                    HttpStatus.NOT_FOUND);
        } else
        if(response.equals("email not verified")) {
            body.put("message","L'email n'est pas encore vérifié ! Veuillez consulter votre boite de reception pour vérifier votre email;");
            return new ResponseEntity<>(
                    body,
                    HttpStatus.UNAUTHORIZED
            );
        } else {
            body.put("jwt",response);
            return new ResponseEntity<>(

                    body,
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
                body.put("message","Bravo, le code est correct ! Vous allez rediriger vers la page connexion.");
                return new ResponseEntity<>(body, HttpStatus.OK);
                }
                else
                {
                    body.put("message","Le code doit être composé de 4 chiffres !");
                    return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
                }
            }
            else{
                body.put("message","Désolé, le code que vous avez saisi est incorrect, veuillez saisir le bon code !");
                return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);

            }
        }catch (UserNotFound e){
            body.put("message","L'utilisateur n'est pas trouvé !");
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
                body.put("message", "Un email vous est envoyé pour la réinitialisation.");
                return new ResponseEntity<>(
                        body,
                        HttpStatus.OK
                );
            }else{
                body.put("message", "Une erreur est survenue ! Veuillez réessayer !");
                return new ResponseEntity<>(
                        body,
                        HttpStatus.INTERNAL_SERVER_ERROR
                );
            }

        }catch(UserNotFound e){
            body.put("message", "L'utilisateur n'est pas trouvé !");
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
            body.put("message","Le mot de passe a été changé !");
            return new ResponseEntity<>(
                    body,
                    HttpStatus.OK
            );
        }catch(UserNotFound e){
            body.put("Message", "L'utilisateur n'est pas trouvé !");
            return new ResponseEntity<>(
                    body,
                    HttpStatus.NOT_FOUND
            );
        }catch(JWTException e){

            body.put("Message", "Le lien est invalid, veuillez réessayer !");
            return new ResponseEntity<>(
                    body,
                    HttpStatus.UNAUTHORIZED
            );

        }
    }
}
