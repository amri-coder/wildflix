package com.wildflix.wildflix.services;

import com.wildflix.wildflix.models.Contact;

public interface ContactService {

    Contact saveContact(Contact contact);
    void sendEmail(Contact contact) throws Exception;
}
