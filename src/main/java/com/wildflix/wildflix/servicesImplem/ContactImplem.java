package com.wildflix.wildflix.servicesImplem;


import com.wildflix.wildflix.models.Category;
import com.wildflix.wildflix.models.Contact;
import com.wildflix.wildflix.models.Video;
import com.wildflix.wildflix.repository.ContactRepository;
import com.wildflix.wildflix.services.ContactService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ContactImplem implements ContactService {

    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private JavaMailSender javaMailSender;


    @Override
    public Contact saveContact(Contact contact) {
        return contactRepository.save(contact);
    }


    public void sendEmail(Contact contact) throws Exception{
        //SimpleMailMessage message = new SimpleMailMessage();
       // message.setTo(toUser);
        //message.setSubject(subject);
      //  message.setText(body);
        //emailSender.send(message);


        MimeMessageHelper messageHelper = new MimeMessageHelper(javaMailSender.createMimeMessage(), false, "utf-8");
        messageHelper.setFrom(contact.getEmail());
        messageHelper.setTo("amri.maher@yahoo.com"); // Remplacez avec l'adresse e-mail du destinataire (votre adresse)
        messageHelper.setSubject("Nouveau message de contact");

        String emailContent = "Nom: " + contact.getName() + "<br>"
                + "E-mail: " + contact.getEmail() + "<br>"
                + "Téléphone: " + contact.getPhone() + "<br>"
                + "Sujet: " + contact.getSubject() + "<br>"
                + "Message: " + contact.getMessage();

        messageHelper.setText(emailContent, true);

        javaMailSender.send(messageHelper.getMimeMessage());
    }
    }

