package com.wildflix.wildflix.services;

public interface EmailService {
    void sendEmail(String toUser, String subject, String body);
}
