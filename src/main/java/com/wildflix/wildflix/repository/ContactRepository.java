package com.wildflix.wildflix.repository;

import com.wildflix.wildflix.models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    Contact save(Contact contact);
}
