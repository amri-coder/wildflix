package com.wildflix.wildflix.services;


import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

public interface EmailService {
    public void sendEmail(String ToUser, String subject, String body);

}
