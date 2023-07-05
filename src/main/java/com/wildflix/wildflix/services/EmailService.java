package com.wildflix.wildflix.services;

public interface EmailService {
    public void sendEmail(String toUser, String subject, String body);
}
