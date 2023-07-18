package com.wildflix.wildflix.controllers;

import com.wildflix.wildflix.models.Contact;
import com.wildflix.wildflix.repository.ContactRepository;
import com.wildflix.wildflix.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ContactController {

    @Autowired
    ContactService contactService;
    @Autowired
    ContactRepository contactRepository;

    @PostMapping("/contact")
    public ResponseEntity<?> receiveContactForm(@RequestBody Contact contact) {
        // Enregistrer le message de contact dans la base de données
        contactService.saveContact(contact);
        Map<String, Object> body = new HashMap<>();
        // Envoyer l'e-mail
        try {
            body.put("message", "Formulaire envoyé avec succes !");
            contactService.sendEmail(contact);
           return new ResponseEntity<>(body,
                   HttpStatus.OK);

        } catch (Exception e) {
            body.put("message","Erreur lors de l'envoi de l'e-mail.");
            return new ResponseEntity<>(body,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
       // return ResponseEntity.ok("Formulaire de contact reçu avec succès !");
    }
}
